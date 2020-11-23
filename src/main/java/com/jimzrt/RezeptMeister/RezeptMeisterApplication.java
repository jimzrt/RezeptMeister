package com.jimzrt.RezeptMeister;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jimzrt.RezeptMeister.model.Amount;
import com.jimzrt.RezeptMeister.model.Difficulty;
import com.jimzrt.RezeptMeister.model.Ingredient;
import com.jimzrt.RezeptMeister.model.IngredientGroup;
import com.jimzrt.RezeptMeister.model.Nutrition;
import com.jimzrt.RezeptMeister.model.Recipe;
import com.jimzrt.RezeptMeister.model.Tag;
import com.jimzrt.RezeptMeister.model.Unit;
import com.jimzrt.RezeptMeister.model.external.EdekaResponse;
import com.jimzrt.RezeptMeister.repositories.IngredientRepository;
import com.jimzrt.RezeptMeister.repositories.RecipeRepository;
import com.jimzrt.RezeptMeister.repositories.TagRepository;
import com.jimzrt.RezeptMeister.repositories.UnitRepository;

@SpringBootApplication
public class RezeptMeisterApplication {

	private static final Logger log = LoggerFactory.getLogger(RezeptMeisterApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RezeptMeisterApplication.class, args);
	}

	@Autowired
	RecipeRepository recipeRepository;

	@Autowired
	IngredientRepository ingredientRepository;

	@Autowired
	UnitRepository unitRepository;

	@Autowired
	TagRepository tagRepository;

//	@Autowired
//	NutritionRepository nutritionRepository;

	// decompress a Gzip file into a byte arrays
	public static byte[] decompressGzipToBytes(InputStream source) throws IOException {

		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try (GZIPInputStream gis = new GZIPInputStream(source)) {

			// copy GZIPInputStream to ByteArrayOutputStream
			byte[] buffer = new byte[1024];
			int len;
			while ((len = gis.read(buffer)) > 0) {
				output.write(buffer, 0, len);
			}

		}

		return output.toByteArray();

	}

	@Autowired
	private ResourceLoader resourceLoader;
	
//	@Autowired
//	private EntityManager entityManager;
//	
//	@Bean
//	@Order(2)
//	public CommandLineRunner buildLuceneIndex() {
//		return args -> {
//			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
//			try {
//				fullTextEntityManager.createIndexer().startAndWait();
//			} catch (InterruptedException e) {
//				System.out.println("Error occured trying to build Hibernate Search indexes "
//						+ e.toString());
//			}
//		};
//	}

	@Bean
	@Order(1)
	public CommandLineRunner run() throws Exception {
		return args -> {

			if (recipeRepository.count() > 0) {
				log.info(String.format("Got %d recipes, skip database init", recipeRepository.count()));
				log.info("Recreate database for new import!");
				return;
			}

//			Resource resource = new ClassPathResource("/import/merged_edeka.json.gz");
//		    File file = resource.getFile();
//		    
			// File file = new
			// File(getClass().getResource("import/merged_edeka.json.gz").getFile());
			// File file = new
			// File(String.valueOf(this.getClass().getResourceAsStream("/import/merged_edeka.json.gz")));
			// InputStream is =
			// Thread.currentThread().getContextClassLoader().getResourceAsStream("/import/merged_edeka.json.gz");

			Resource resource = resourceLoader.getResource("file:import/merged_edeka.json.gz");
			InputStream is = null;
			try {
				is = resource.getInputStream();

			} catch (IOException exception) {
				log.error("import/merged_edeka.json does not exist! Check Readme in import-folder");
				return;
			}

			if (is == null) {
				log.error("import/merged_edeka.json does not exist! Check Readme in import-folder");
				return;
			}

			log.info("Starting import");
			ObjectMapper objectMapper = new ObjectMapper();

			EdekaResponse edekaResponse = objectMapper.readValue(decompressGzipToBytes(is), EdekaResponse.class);
			StopWatch watch = new StopWatch();
			watch.start();

			for (var edekaRecipe : edekaResponse.getRecipes()) {
				Recipe recipe = recipeRepository.findBySeoTitle(edekaRecipe.getSeoTitle());
				if (recipe != null) {
					log.info(String.format("ignore duplicate recipe: %s", recipe.getTitle()));
					continue;
				}
				recipe = new Recipe();
				recipe.setTitle(edekaRecipe.getTitle());
				recipe.setSeoTitle(edekaRecipe.getSeoTitle());

				resource = resourceLoader.getResource(
						String.format("file:static/images/recipe/%s_big.jpg", edekaRecipe.getSeoTitle()));
				InputStream pictureInputstream = null;
				try {
					pictureInputstream = resource.getInputStream();
				} catch (IOException exception) {
					log.info(String.format("ignore recipe without picture: %s", recipe.getTitle()));
					continue;
				}
				// InputStream pictureInputstream =
				// getClass().getClassLoader().getResourceAsStream(String.format("classpath:static/images/recipe/%s_big.jpg",
				// edekaRecipe.getSeoTitle()));
				// File pictureFile = new
				// File(String.format("src/main/resources/static/images/recipe/%s_big.jpg",
				// edekaRecipe.getSeoTitle()));
				if (pictureInputstream == null) {
					log.info(String.format("ignore recipe without picture: %s", recipe.getTitle()));
					continue;
				}
				recipe.setPictureUrl(String.format("images/recipe/%s_big.jpg", edekaRecipe.getSeoTitle()));

				recipe.setDifficulty(Difficulty.valueOf(edekaRecipe.getDifficulty()));
				recipe.setDescription(edekaRecipe.getDescriptions().getMetaDesc());
				recipe.setDefaultServingSize(edekaRecipe.getServings());
				Nutrition nutrition = new Nutrition();
				nutrition.setCarbohydrates(edekaRecipe.getNutritions().getCarbohydrates());
				nutrition.setCholesterol(edekaRecipe.getNutritions().getCholesterol());
				nutrition.setFat(edekaRecipe.getNutritions().getFat());
				nutrition.setKcal(edekaRecipe.getNutritions().getKcal());
				recipe.setCalories(edekaRecipe.getNutritions().getKcal());
				nutrition.setKj(edekaRecipe.getNutritions().getKj());
				nutrition.setProtein(edekaRecipe.getNutritions().getProtein());
				nutrition.setRoughage(edekaRecipe.getNutritions().getRoughage());
				recipe.setNutrition(nutrition);
				recipe.setDirections(edekaRecipe.getDirections());
				var prepTime = edekaRecipe.getPreparationTime();
				Duration d = Duration.parse(prepTime);
				recipe.setPreperationTimeInSeconds(d.toSeconds());
				var totalTime = edekaRecipe.getTotalTime();
				d = Duration.parse(totalTime);
				recipe.setTotalTimeInSeconds(d.toSeconds());
				var edekaTags = edekaRecipe.getTags();
				if (edekaTags != null) {
					for (var edekaTag : edekaTags) {
						Tag tag = tagRepository.findByName(edekaTag.getName());
						if (tag == null) {
							tag = new Tag();
							tag.setName(edekaTag.getName());
							tagRepository.save(tag);
						}
						recipe.getTags().add(tag);
					}
				}

				for (var edekaIngredientGroup : edekaRecipe.getIngredientGroups()) {
					IngredientGroup ingredientGroup = new IngredientGroup();
					ingredientGroup
							.setName(edekaIngredientGroup.getName() == null ? "" : edekaIngredientGroup.getName());
					Map<Ingredient, Amount> amountMap = new HashMap<Ingredient, Amount>();
					for (var edekaGroupIngredient : edekaIngredientGroup.getIngredientGroupIngredients()) {

						Amount amount = new Amount();
						amount.setQuantity(edekaGroupIngredient.getQuantity());
						Unit unit = unitRepository.findByName(
								edekaGroupIngredient.getUnit() == null ? "" : edekaGroupIngredient.getUnit());
						if (unit == null) {
							unit = new Unit(
									edekaGroupIngredient.getUnit() == null ? "" : edekaGroupIngredient.getUnit());
							unitRepository.save(unit);
						}
						amount.setUnit(unit);
						var ingredientName = edekaGroupIngredient.getIngredient().trim();
						Ingredient ingredient = ingredientRepository.findByName(ingredientName);
						if (ingredient == null) {
							var ingredientSlug = Ingredient.makeSlugForName(ingredientName);
							ingredient = new Ingredient(ingredientName, ingredientSlug);
							ingredient.setPictureUrl(String.format("/images/ingredient/%s.jpg", ingredientSlug));
							ingredientRepository.save(ingredient);
						}

						amountMap.put(ingredient, amount);

					}
					ingredientGroup.setAmounts(amountMap);
					recipe.addAmountGroup(ingredientGroup);
				}
				recipeRepository.save(recipe);
			}

			watch.stop();
			log.info(String.format("saving took %s ms", watch.getTotalTimeMillis()));
			int recipeCount = edekaResponse.getRecipes().size();
			log.info("Got " + recipeCount + " recipes");
			Thread.sleep(1000L);
			// }

			// get images for ingredients (scrape bing)
//			for (String ingredient : collect) {
//				Document doc = Jsoup
//						.connect(String.format("http://www.bing.com/images/search?q=%s&FORM=HDRSC2",
//								URLEncoder.encode(ingredient, StandardCharsets.UTF_8.toString())))
//						.userAgent(
//								"Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
//						.get();
//				Elements newsHeadlines = doc.select("a.iusc");
//				if(newsHeadlines.size() > 0) {
//					ObjectMapper mapper = new ObjectMapper();
//
//					// convert JSON file to map
//					Map<?, ?> map = mapper.readValue(newsHeadlines.get(0).attr("m"), Map.class);
//					log.info(String.format("%s: %s",ingredient, map.get("turl")));
//				} else {
//					System.exit(0);
//				}
//				Thread.sleep(1000L);
//			}
			// log.info("" + collect.size());
			log.info("Init done!");
		};
	}

//	@Bean
//	@Order(2)
//	public CommandLineRunner demo(RecipeRepository repo, IngredientRepository inRepo) {
//		return (args) -> {
//
//			Ingredient kartoffel = inRepo.findByName("Kartoffel");
//			if (kartoffel == null) {
//				kartoffel = new Ingredient("Kartoffel");
//				inRepo.save(kartoffel);
//			}
//			Ingredient tomate = inRepo.findByName("Tomate");
//			if (tomate == null) {
//				tomate = new Ingredient("Tomate");
//				inRepo.save(tomate);
//			}
//			Ingredient zwiebel = inRepo.findByName("Zwiebel");
//			if (zwiebel == null) {
//				zwiebel = new Ingredient("Zwiebel");
//				inRepo.save(zwiebel);
//			}
//
//			Map<Ingredient, Amount> amountMap = new HashMap<Ingredient, Amount>();
//			amountMap.put(kartoffel, new Amount(new Unit("KG"), 200f));
//			amountMap.put(tomate, new Amount(new Unit("KG"), 100f));
//			amountMap.put(zwiebel, new Amount(new Unit("G"), 500f));
//
//			IngredientGroup amountGroup = new IngredientGroup("Prep", amountMap);
//
//			Recipe recp = new Recipe();
//			recp.setTitle("Kartoffelsalat");
//			recp.setDescription("descr");
//			recp.setDifficulty(Difficulty.EASY);
//			recp.setDefaultServingSize(4);
//			recp.addAmountGroup(amountGroup);
//
//			repo.save(recp);
//
//			List<Recipe> findByamountGroupsAmounts = repo.findDistinctByIngredientsNameContainingIgnoreCase("z");
//			for (Recipe recipe : findByamountGroupsAmounts) {
//				log.info(recipe.getTitle());
//			}
//
//		};
//	}

}

package com.jimzrt.RezeptMeister;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jimzrt.RezeptMeister.model.Amount;
import com.jimzrt.RezeptMeister.model.IngredientGroup;
import com.jimzrt.RezeptMeister.model.Nutrition;
import com.jimzrt.RezeptMeister.model.Difficulty;
import com.jimzrt.RezeptMeister.model.Ingredient;
import com.jimzrt.RezeptMeister.model.Recipe;
import com.jimzrt.RezeptMeister.model.Unit;
import com.jimzrt.RezeptMeister.model.external.EdekaResponse;
import com.jimzrt.RezeptMeister.model.external.EdekaResponse.EdekaRecipe;
import com.jimzrt.RezeptMeister.repositories.IngredientRepository;
import com.jimzrt.RezeptMeister.repositories.NutritionRepository;
import com.jimzrt.RezeptMeister.repositories.RecipeRepository;
import com.jimzrt.RezeptMeister.repositories.UnitRepository;

import lombok.NonNull;

@SpringBootApplication
public class RezeptMeisterApplication {

	private static final Logger log = LoggerFactory.getLogger(RezeptMeisterApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RezeptMeisterApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	IngredientRepository ingredientRepository;
	
	@Autowired
	UnitRepository unitRepository;
	
//	@Autowired
//	NutritionRepository nutritionRepository;
	
	@Bean
	@Order(1)
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			
			
			if(recipeRepository.count() > 0) {
				log.info("Got more than 1 recipe, skip database init");
				return;
			}
			
			int pageCount = 80;
			while (true) {

				log.info("Getting page " + pageCount);
				EdekaResponse edekaResponse = restTemplate.getForObject(
						"https://www.edeka.de/rezepte/rezept/suche?query=&page=" + pageCount + " &size=50",
						EdekaResponse.class);

				if (edekaResponse.getRecipes() == null) {
					break;
				}
				StopWatch watch = new StopWatch();
		        watch.start();
		        
		        for(var edekaRecipe : edekaResponse.getRecipes()){
		        	Recipe recipe = new Recipe();
		        	recipe.setTitle(edekaRecipe.getTitle());
		        	recipe.setSeoTitle(edekaRecipe.getSeoTitle());
		        	recipe.setDifficulty(Difficulty.valueOf(edekaRecipe.getDifficulty()));
		        	recipe.setDescription(edekaRecipe.getDescriptions().getMetaDesc());
		        	recipe.setDefaultServingSize(edekaRecipe.getServings());
		        	Nutrition nutrition = new Nutrition();
		        	nutrition.setCarohydrates(edekaRecipe.getNutrition().getCarohydrates());
		        	nutrition.setCholesterol(edekaRecipe.getNutrition().getCholesterol());
		        	nutrition.setFat(edekaRecipe.getNutrition().getFat());
		        	nutrition.setKcal(edekaRecipe.getNutrition().getKcal());
		        	nutrition.setKj(edekaRecipe.getNutrition().getKj());
		        	nutrition.setProtein(edekaRecipe.getNutrition().getProtein());
		        	nutrition.setRoughage(edekaRecipe.getNutrition().getRoughage());
		  
		        	recipe.setNutrition(nutrition);
		        	
		        	for(var edekaIngredientGroup : edekaRecipe.getIngredientGroups()) {
		        		IngredientGroup ingredientGroup = new IngredientGroup();
		        		ingredientGroup.setName(edekaIngredientGroup.getName() == null ? "" : edekaIngredientGroup.getName());
		        		Map<Ingredient, Amount> amountMap = new HashMap<Ingredient, Amount>();
		        		for(var edekaGroupIngredient : edekaIngredientGroup.getIngredientGroupIngredients()) {
		        			
		        			Amount amount = new Amount();
		        			amount.setQuantity(edekaGroupIngredient.getQuantity());
		        			Unit unit = unitRepository.findByName(edekaGroupIngredient.getUnit() == null ? "" : edekaGroupIngredient.getUnit());
		        			if(unit == null) {
		        				unit = new Unit(edekaGroupIngredient.getUnit() == null ? "" : edekaGroupIngredient.getUnit());
		        				unitRepository.save(unit);
		        			}
		        			amount.setUnit(unit);
		        			Ingredient ingredient = ingredientRepository.findByName(edekaGroupIngredient.getIngredient());
		        			if(ingredient == null) {
		        				ingredient = new Ingredient(edekaGroupIngredient.getIngredient());
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
				pageCount++;
				int recipeCount = edekaResponse.getRecipes().size();
				log.info("Got " + recipeCount + " recipes");
				Thread.sleep(1000L);
			}
			
			
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
			//log.info("" + collect.size());
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

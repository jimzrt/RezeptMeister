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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jimzrt.RezeptMeister.EdekaResponse.EdekaRecipe;

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

	@Bean
	@Order(1)
	public CommandLineRunner run(RestTemplate restTemplate,RecipeRepository recipeRepository, IngredientRepository ingredientRepository, UnitRepository unitRepository) throws Exception {
		return args -> {

			int recipeCount = 0;
			int pageCount = 1;
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
//		        Set<String> collect = edekaResponse.getRecipes().stream()
//						.flatMap(r -> r.getIngredientGroups().stream()
//								.flatMap(g -> g.getIngredientGroupIngredients().stream().map(gi -> gi.getIngredient())))
//						.collect(Collectors.toSet());
		        
		        for(var edekaRecipe : edekaResponse.getRecipes()){
		        	Recipe recipe = new Recipe();
		        	recipe.setTitle(edekaRecipe.getTitle());
		        	recipe.setDifficulty(Difficulty.valueOf(edekaRecipe.getDifficulty()));
		        	recipe.setDescription(edekaRecipe.getDescriptions().getMetaDesc());
		        	recipe.setDefaultServingSize(edekaRecipe.getServings());
		        	for(var edekaIngredientGroup : edekaRecipe.getIngredientGroups()) {
		        		AmountGroup amountGroup = new AmountGroup();
		        		amountGroup.setName(edekaIngredientGroup.getName() == null ? "" : edekaIngredientGroup.getName());
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
		        					//amount.setUnit();
		        			//amountMap.put(edekaGroupIngredient.getIngredient(), );
		        			
		        		}
		        		amountGroup.setAmounts(amountMap);
		        		recipe.addAmountGroup(amountGroup);
		        	}
		        	recipeRepository.save(recipe);
		        }
		        
		       // collect.stream().forEach(in->inRepo.save(new Ingredient(in)));
				
				watch.stop();
				log.info(String.format("saving took %s ms", watch.getTotalTimeMillis()));
				pageCount++;
				recipeCount = edekaResponse.getRecipes().size();
				log.info("Got " + recipeCount + " recipes");
				Thread.sleep(5000L);
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
//			AmountGroup amountGroup = new AmountGroup("Prep", amountMap);
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

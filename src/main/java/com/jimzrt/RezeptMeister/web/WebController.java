package com.jimzrt.RezeptMeister.web;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jimzrt.RezeptMeister.model.Ingredient;
import com.jimzrt.RezeptMeister.model.Recipe;
import com.jimzrt.RezeptMeister.model.Tag;
import com.jimzrt.RezeptMeister.model.external.SearchFilter;
import com.jimzrt.RezeptMeister.model.specification.GenericSpecificationsBuilder;
import com.jimzrt.RezeptMeister.model.specification.SearchOperation;
import com.jimzrt.RezeptMeister.repositories.IngredientRepository;
import com.jimzrt.RezeptMeister.repositories.RecipeRepository;
import com.jimzrt.RezeptMeister.repositories.TagRepository;

@RestController
public class WebController {

	private static final Logger log = LoggerFactory.getLogger(WebController.class);

	@Autowired
	RecipeRepository recipeRepository;

	@Autowired
	IngredientRepository ingredientRepository;

	@Autowired
	TagRepository tagRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@RequestMapping(value = "/ingredient/{ingredientName}", method = RequestMethod.POST)
	public List<Ingredient> findIngredient(@RequestBody SearchFilter searchFilter,
			@PathVariable String ingredientName) {

		int count = 15;
		Pageable pageable = PageRequest.of(0, count);

		// TODO do this on database layer
		Set<Ingredient> all = new HashSet<Ingredient>(
				ingredientRepository.findByNameIgnoreCase(ingredientName, pageable));

		all.addAll(ingredientRepository.findByNameStartingWithIgnoreCase(ingredientName, pageable));
		all.addAll(ingredientRepository.findByNameContainingIgnoreCase(ingredientName, pageable));
		var ingredients = all.parallelStream().map(ingredient -> {
			GenericSpecificationsBuilder<Recipe> builder = new GenericSpecificationsBuilder<>();
			builder.with("ingredients.id", SearchOperation.EQUAL, Collections.singletonList(ingredient.getId()));
			var specification = builder.fromFilter(searchFilter);
			ingredient.setRecipeCount(recipeRepository.count(specification));
			return ingredient;
		}).filter(ingredient -> ingredient.getRecipeCount() != 0)
				.sorted(Comparator.comparingLong(Ingredient::getRecipeCount).reversed()).limit(count)
				.collect(Collectors.toList());

		return ingredients;

	}

	@RequestMapping("/recipe")
	public List<Recipe> findRecipe(@RequestParam("name") String name,
			@RequestParam("count") Optional<Integer> countOpt) {

		if (countOpt.isPresent()) {
			Pageable pageable = PageRequest.of(0, countOpt.get());
			return recipeRepository.findByTitleContainingIgnoreCase(name, pageable);
		}

		return recipeRepository.findByTitleContainingIgnoreCase(name, Pageable.unpaged());

	}

	@RequestMapping("/recipe/seo")
	public Recipe findRecipe(@RequestParam("seoTitle") String seoTitle) {

		var recipe = recipeRepository.findBySeoTitle(seoTitle);

		// init lazy-loaded collections
		Hibernate.initialize(recipe.getDirections());
		Hibernate.initialize(recipe.getIngredients());
		Hibernate.initialize(recipe.getTags());
		Hibernate.initialize(recipe.getNutrition());
		Hibernate.initialize(recipe.getIngredientGroups());

		return recipe;

	}

	@RequestMapping(value = "/recipe/count/id/{ingredientId}", method = RequestMethod.POST)
	public long countRecipesByIngredientId(@RequestBody SearchFilter searchFilter, @PathVariable long ingredientId) {

		System.out.println("count: " + searchFilter);

		StopWatch watch = new StopWatch();
		watch.start();

		GenericSpecificationsBuilder<Recipe> builder = new GenericSpecificationsBuilder<>();
		builder.with("ingredients.id", SearchOperation.EQUAL, Collections.singletonList(ingredientId));
		var specification = builder.fromFilter(searchFilter);

		long recipeCount = recipeRepository.count(specification);

		watch.stop();
		log.info(String.format("searching (count) took %s ms", watch.getTotalTimeMillis()));
		return recipeCount;
	}

	@RequestMapping(value = "/recipe/count/name/{ingredientName}", method = RequestMethod.POST)
	public long countRecipesByIngredientName(@RequestBody SearchFilter searchFilter,
			@PathVariable String ingredientName) {

		System.out.println("count: " + searchFilter);

		StopWatch watch = new StopWatch();
		watch.start();

		GenericSpecificationsBuilder<Recipe> builder = new GenericSpecificationsBuilder<>();
		builder.with("ingredients.name", SearchOperation.LIKE, Collections.singletonList(ingredientName));
		var specification = builder.fromFilter(searchFilter);

		long recipeCount = recipeRepository.count(specification);

		watch.stop();
		log.info(String.format("searching (count) took %s ms", watch.getTotalTimeMillis()));
		return recipeCount;
	}

	@RequestMapping(value = "/recipe/search", method = RequestMethod.POST)
	public Page<Recipe> getBySearch(@RequestBody SearchFilter searchFilter) {
		System.out.println("search: " + searchFilter);

		StopWatch watch = new StopWatch();
		watch.start();

		Pageable pageable = PageRequest.of(searchFilter.getPage(), searchFilter.getCount());

		GenericSpecificationsBuilder<Recipe> builder = new GenericSpecificationsBuilder<>();
		var specification = builder.fromFilter(searchFilter);

		Page<Recipe> recipes = recipeRepository.findAll(specification, pageable);
		recipes.get().forEach(r -> {
			r.getIngredients().size();
			r.getTags().size();
		});
		watch.stop();
		log.info(String.format("searching (search) took %s ms", watch.getTotalTimeMillis()));
		return recipes;

	}

	@RequestMapping("/tag")
	public List<Tag> findTag(@RequestParam("name") String name, @RequestParam("count") Optional<Integer> countOpt) {

		if (countOpt.isPresent()) {
			Pageable pageable = PageRequest.of(0, countOpt.get());
			return tagRepository.findByNameContainingIgnoreCase(name, pageable);
		}

		return tagRepository.findByNameContainingIgnoreCase(name);
	}

}
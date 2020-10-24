package com.jimzrt.RezeptMeister;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	IngredientRepository ingredientRepository;

	@RequestMapping("/ingredient")
	public List<Ingredient> findIngredient(@RequestParam("name") String name) {
		return ingredientRepository.findByNameContainingIgnoreCase(name);
	}
	
	@RequestMapping("/recipe")
	public List<Recipe> findRecipe(@RequestParam("name") String name) {
		return recipeRepository.findDistinctByIngredientsNameContainingIgnoreCase(name);
	}

}
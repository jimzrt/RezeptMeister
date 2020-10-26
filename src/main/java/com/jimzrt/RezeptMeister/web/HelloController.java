package com.jimzrt.RezeptMeister.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jimzrt.RezeptMeister.model.Ingredient;
import com.jimzrt.RezeptMeister.model.Recipe;
import com.jimzrt.RezeptMeister.repositories.IngredientRepository;
import com.jimzrt.RezeptMeister.repositories.RecipeRepository;

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
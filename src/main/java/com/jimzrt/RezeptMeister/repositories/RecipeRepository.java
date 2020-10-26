package com.jimzrt.RezeptMeister.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jimzrt.RezeptMeister.model.Recipe;

public interface RecipeRepository  extends CrudRepository<Recipe, Long>{

	List<Recipe> findByTitle(String title);

	  Recipe findById(long id);
	  
	  List<Recipe> findDistinctByIngredientsNameContainingIgnoreCase(String name);
}

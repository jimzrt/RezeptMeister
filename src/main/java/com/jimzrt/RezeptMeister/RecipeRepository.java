package com.jimzrt.RezeptMeister;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository  extends CrudRepository<Recipe, Long>{

	List<Recipe> findByTitle(String title);

	  Recipe findById(long id);
	  
	  List<Recipe> findDistinctByIngredientsNameContainingIgnoreCase(String name);
}

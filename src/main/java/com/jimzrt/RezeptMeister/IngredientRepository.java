package com.jimzrt.RezeptMeister;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long>{

	 Ingredient findByName(String name);
	 List<Ingredient> findByNameContainingIgnoreCase(String name);
}

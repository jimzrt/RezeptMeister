package com.jimzrt.RezeptMeister.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jimzrt.RezeptMeister.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long>{

	 Ingredient findByName(String name);
	 List<Ingredient> findByNameContainingIgnoreCase(String name);
}

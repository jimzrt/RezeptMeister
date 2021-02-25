package com.jimzrt.RezeptMeister.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import com.jimzrt.RezeptMeister.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

	List<Recipe> findByTitle(String title);

	Recipe findBySeoTitle(String seoTitle);

	Recipe findById(long id);

	List<Recipe> findDistinctByIngredientsNameContainingIgnoreCase(String name);

	List<Recipe> findDistinctByIngredientsName(String name);

	Long countDistinctByIngredientsName(String name);

	Long countDistinctByIngredientsId(long id);

	List<Recipe> findByTitleContainingIgnoreCase(String name, Pageable pageable);

	List<Recipe> findByTitleContainingIgnoreCase(String name);

	@Override
	Page<Recipe> findAll(@Nullable Specification<Recipe> spec, Pageable pageable);
}

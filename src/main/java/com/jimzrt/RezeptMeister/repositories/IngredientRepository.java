package com.jimzrt.RezeptMeister.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.jimzrt.RezeptMeister.model.Ingredient;

public interface IngredientRepository
		extends PagingAndSortingRepository<Ingredient, Long>, JpaSpecificationExecutor<Ingredient> {

	Ingredient findByName(String name);

	// List<Ingredient> findByNameContainingIgnoreCase(String name, Pageable
	// pageable);

	List<Ingredient> findByNameContainingIgnoreCase(String name);

	@Query("select i from Ingredient i where UPPER(i.name) like UPPER(concat('%', :name,'%')) ORDER BY\n" + "  CASE\n"
			+ "    WHEN UPPER(i.name) LIKE UPPER(concat(:name,'%')) THEN 1\n"
			+ "    WHEN UPPER(i.name) LIKE UPPER(concat('%', :name)) THEN 3\n" + "    ELSE 2\n"
			+ "  END, LENGTH(i.name)")
	List<Ingredient> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

	List<Ingredient> findByNameStartingWithIgnoreCase(String name, Pageable pageable);

}

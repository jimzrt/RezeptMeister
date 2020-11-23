package com.jimzrt.RezeptMeister.repositories;

import org.springframework.data.repository.CrudRepository;

import com.jimzrt.RezeptMeister.model.Nutrition;

public interface NutritionRepository extends CrudRepository<Nutrition, Long> {

}

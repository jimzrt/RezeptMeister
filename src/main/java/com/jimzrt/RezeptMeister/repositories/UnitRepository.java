package com.jimzrt.RezeptMeister.repositories;

import org.springframework.data.repository.CrudRepository;

import com.jimzrt.RezeptMeister.model.Unit;

public interface UnitRepository extends CrudRepository<Unit, Long>{
	Unit findByName(String name);
}

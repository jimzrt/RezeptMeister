package com.jimzrt.RezeptMeister;

import org.springframework.data.repository.CrudRepository;

public interface UnitRepository extends CrudRepository<Unit, Long>{
	Unit findByName(String name);
}

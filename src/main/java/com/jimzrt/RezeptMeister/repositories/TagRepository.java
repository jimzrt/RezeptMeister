package com.jimzrt.RezeptMeister.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.jimzrt.RezeptMeister.model.Tag;

public interface TagRepository extends CrudRepository<Tag, Long> {

	Tag findByName(String name);

	List<Tag> findByNameContainingIgnoreCase(String name);

	List<Tag> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

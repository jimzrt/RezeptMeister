package com.jimzrt.RezeptMeister.model.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

@FunctionalInterface
public interface PathSpecification<T> {

	default Specification<T> atRoot() {
		return this::toPredicate;
	}

	default <S> Specification<S> atPath(final String string) {
		// you'll need a couple more methods like this one for all flavors of attribute
		// types in order to make it fully workable
		return (root, query, cb) -> {
			return toPredicate(root.join(string), query, cb);
		};
	}

	@Nullable
	Predicate toPredicate(Path<T> path, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
}
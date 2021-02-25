package com.jimzrt.RezeptMeister.model.specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import com.jimzrt.RezeptMeister.model.Difficulty;
import com.jimzrt.RezeptMeister.model.external.SearchFilter;

public class GenericSpecificationsBuilder<T> {

	private final List<SearchCriteria> params;
	private final List<Specification<T>> specifications;

	public GenericSpecificationsBuilder() {
		this.params = new ArrayList<>();
		this.specifications = new ArrayList<>();
	}

	public final GenericSpecificationsBuilder<T> with(final String key, final SearchOperation searchOperation,
			final List<Object> arguments) {
		return with(key, searchOperation, false, arguments);
	}

	public final GenericSpecificationsBuilder<T> with(final String key, final SearchOperation searchOperation,
			final boolean isOrOperation, final List<Object> arguments) {
		params.add(new SearchCriteria(key, searchOperation, isOrOperation, arguments));
		return this;
	}

	public final GenericSpecificationsBuilder<T> with(Specification<T> specification) {
		specifications.add(specification);
		return this;
	}

	public Specification<T> build() {
		Specification<T> result = null;
		if (!params.isEmpty()) {
			result = new GenericSpecification<>(params.get(0));
			for (int index = 1; index < params.size(); ++index) {
				SearchCriteria searchCriteria = params.get(index);
				result = searchCriteria.isOrOperation()
						? Specification.where(result).or(new GenericSpecification<>(searchCriteria))
						: Specification.where(result).and(new GenericSpecification<>(searchCriteria));
			}
		}
		if (!specifications.isEmpty()) {
			int index = 0;
			if (Objects.isNull(result)) {
				result = specifications.get(index++);
			}
			for (; index < specifications.size(); ++index) {
				result = Specification.where(result).and(specifications.get(index));
			}
		}
		return result;
	}

	public Specification<T> fromFilter(SearchFilter searchFilter) {
//		if (searchFilter.getIngredient_special().size() > 0) {
//			for (var ingredientId : searchFilter.getIngredient_special()) {
//				with("ingredients", SearchOperation.LIKE_NAME, Collections.singletonList(ingredientId));
//			}
//		}
//		if (searchFilter.getExclude().getIngredient_special().size() > 0) {
//			for (var ingredientId : searchFilter.getExclude().getIngredient_special()) {
//				with("ingredients", SearchOperation.NOT_LIKE, Collections.singletonList(ingredientId));
//			}
//		}

		if (searchFilter.getIngredient().size() > 0) {
			List<Object> contains = searchFilter.getIngredient().stream()
					.filter(ingredient -> !ingredient.getExclude() && !ingredient.getWildcard())
					.map(ingredient -> ingredient.getId()).collect(Collectors.toList());
			if(!contains.isEmpty())
				with("ingredients", SearchOperation.CONTAINS, contains);
			
			
			List<Object> notEqual = searchFilter.getIngredient().stream()
					.filter(ingredient -> ingredient.getExclude() && !ingredient.getWildcard())
					.map(ingredient -> ingredient.getId()).collect(Collectors.toList());
			if(!notEqual.isEmpty())
				with("ingredients.id", SearchOperation.NOT_EQUAL, notEqual);
			
			List<Object> containsLike = searchFilter.getIngredient().stream()
					.filter(ingredient -> !ingredient.getExclude() && ingredient.getWildcard())
					.map(ingredient -> ingredient.getId()).collect(Collectors.toList());
			if(!containsLike.isEmpty())
				with("ingredients", SearchOperation.CONTAINS_LIKE, containsLike);
			
			for (var ingredient : searchFilter.getIngredient()) {
//				if (!ingredient.getExclude() && !ingredient.getWildcard()) {
//					with("ingredients.id", SearchOperation.EQUAL, Collections.singletonList(ingredient.getId()));
//				} else
//				if (ingredient.getExclude() && !ingredient.getWildcard()) {
//					with("ingredients.id", SearchOperation.NOT_EQUAL, Collections.singletonList(ingredient.getId()));
//				} else 
//				if (!ingredient.getExclude() && ingredient.getWildcard()) {
//					with("ingredients.name", SearchOperation.LIKE, Collections.singletonList(ingredient.getId()));
//				} else {
//					with("ingredients.name", SearchOperation.NOT_LIKE, Collections.singletonList(ingredient.getId()));
//				}
			}
		}

		if (searchFilter.getTag().size() > 0) {
			for (var tag : searchFilter.getTag()) {
				if (!tag.getExclude() && !tag.getWildcard()) {
					with("tags.id", SearchOperation.EQUAL, Collections.singletonList(tag.getId()));
				} else if (tag.getExclude() && !tag.getWildcard()) {
					with("tags.id", SearchOperation.NOT_EQUAL, Collections.singletonList(tag.getId()));
				} else if (!tag.getExclude() && tag.getWildcard()) {
					with("tags.name", SearchOperation.LIKE, Collections.singletonList(tag.getId()));
				} else {
					with("tags.name", SearchOperation.NOT_LIKE, Collections.singletonList(tag.getId()));
				}
			}
		}

//		if (searchFilter.getExclude().getIngredient().size() > 0) {
//			with("ingredients", SearchOperation.NOT_EQUAL, (List) searchFilter.getExclude().getIngredient());
////			for(var ingredientId : searchFilter.getExclude().getIngredient()) {
////				builder.with("ingredients", SearchOperation.NOT_EQUAL, Collections.singletonList(ingredientId));
////			}	
//		}

//		if (searchFilter.getTag().size() > 0) {
//			for (var tag : searchFilter.getTag()) {
//				with("tags", SearchOperation.EQUALITY, Collections.singletonList(tag.getId()));
//			}
//		}
//		if (searchFilter.getExclude().getTag().size() > 0) {
//			with("tags", SearchOperation.NOT_EQUAL, (List) searchFilter.getExclude().getTag());
//
//		}

		if (searchFilter.getDifficulty().size() > 0) {
			with("difficulty", SearchOperation.IN, searchFilter.getDifficulty().stream()
					.map(diff -> Difficulty.valueOf(diff)).collect(Collectors.toList()));
		}

		if (searchFilter.getCalories() != 0) {
			with("calories", SearchOperation.LESS_THAN, Arrays.asList(searchFilter.getCalories()));
		}

		if (searchFilter.getTotalTime() != 0) {
			with("totalTimeInSeconds", SearchOperation.LESS_THAN, Arrays.asList(searchFilter.getTotalTime()));
		}

		return build();

	}

}
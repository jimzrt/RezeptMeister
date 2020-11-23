package com.jimzrt.RezeptMeister.model.specification;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria {

	private String key;
	private SearchOperation searchOperation;
	private boolean isOrOperation;
	private List<Object> arguments;
}
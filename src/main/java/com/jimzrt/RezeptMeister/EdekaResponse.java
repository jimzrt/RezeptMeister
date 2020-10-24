package com.jimzrt.RezeptMeister;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EdekaResponse {
	
	
	
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@Data
	static class EdekaRecipe {
		
		@JsonIgnoreProperties(ignoreUnknown = true)
		@Data
		static class Description {
			private String metaDesc;
		}
		
		@JsonIgnoreProperties(ignoreUnknown = true)
		@Data
		static class IngredientGroups {
			@JsonIgnoreProperties(ignoreUnknown = true)
			@Data
			static class IngredientGroupIngredients {
				private String ingredient;
				private String unit;
				private float quantity;
			}
			
			private String name;
			private List<IngredientGroupIngredients> ingredientGroupIngredients;
		}
		
		private String title;
		private int servings;
		private Description descriptions;
		private String difficulty;
		private List<IngredientGroups> ingredientGroups;
	}

	
	private List<EdekaRecipe> recipes = new ArrayList<EdekaRecipe>();
	private int totalCount;
	private int currentPage;
	private int currentSize;
}

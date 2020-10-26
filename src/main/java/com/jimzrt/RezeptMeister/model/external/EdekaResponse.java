package com.jimzrt.RezeptMeister.model.external;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NonNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EdekaResponse {
	
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@Data
	
	public static class EdekaRecipe {
		
		@JsonIgnoreProperties(ignoreUnknown = true)
		@Data
		public static class Description {
			private String metaDesc;
		}
		
		@JsonIgnoreProperties(ignoreUnknown = true)
		@Data
		public static class Nutrition {
			private float kcal;
			private float kj;
			private float carohydrates;
			private float protein;
			private float fat;
			private float cholesterol;
			private float roughage;
		}
		
		
		@JsonIgnoreProperties(ignoreUnknown = true)
		@Data
		public static class IngredientGroups {
			@JsonIgnoreProperties(ignoreUnknown = true)
			@Data
			public static class IngredientGroupIngredients {
				private String ingredient;
				private String unit;
				private float quantity;
			}
			
			private String name;
			private List<IngredientGroupIngredients> ingredientGroupIngredients;
		}
		
		private String title;
		private String seoTitle;
		private int servings;
		private Description descriptions;
		private String difficulty;
		private Nutrition nutrition;
		private List<IngredientGroups> ingredientGroups;
		
	}

	
	private List<EdekaRecipe> recipes = new ArrayList<EdekaRecipe>();
	private int totalCount;
	private int currentPage;
	private int currentSize;
}

package com.jimzrt.RezeptMeister.model.external;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

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
			private float carbohydrates;
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

		@JsonIgnoreProperties(ignoreUnknown = true)
		@Data
		public static class Tag {
			private String name;
		}

		private String title;
		private String seoTitle;
		private int servings;
		private Description descriptions;
		private String difficulty;
		private Nutrition nutritions;
		private List<IngredientGroups> ingredientGroups;
		private List<Tag> tags;
		private List<String> directions = new ArrayList<String>();
		private String preparationTime;
		private String totalTime;

		@SuppressWarnings("unchecked")
		@JsonProperty("recipeStepGroups")
		private void unpackNested(List<Map<String, Object>> recipeStepGroups) {
			// this.brandName = (String)brand.get("name");
			if (recipeStepGroups == null) {
				return;
			}
			for (var recipeStepGroup : recipeStepGroups) {

				List<Map<Object, String>> recipeSteps = (List<Map<Object, String>>) recipeStepGroup.get("recipeSteps");
				if (recipeSteps == null) {
					return;
				}
				for (var recipeStep : recipeSteps) {
					var instruction = recipeStep.get("instruction");
					if (instruction == null) {
						continue;
					}
					this.directions.add(instruction);
				}
			}
		}

//		private String smallPictureUrl;
//		private String bigPictureUrl;

//		@SuppressWarnings("unchecked")
//	    @JsonProperty("media")
//	    private void unpackNested(Map<String,Object> media) {
//	        //this.brandName = (String)brand.get("name");
//	        Map<String,Object> images = (Map<String,Object>)media.get("images");
//	        if(images == null) {
//	        	return;
//	        }
//	        Map<String,Object> small = (Map<String,Object>)images.get("ratio_1:1");
//	        if(small == null) {
//	        	return;
//	        }
//	        Map<String,String> url = (Map<String,String>)small.get("url");
//	        if(url == null) {
//	        	return;
//	        }
//	        this.smallPictureUrl = url.get("medium");
//	        
//	        String identifier = "huge";
//	        Map<String,Object> big = (Map<String,Object>)images.get("ratio_16:9");
//	        if(big == null) {
//	        	big = (Map<String,Object>)images.get("ratio_3:4");
//	        	if(big == null) {
//	        		return;
//	        	}
//	        	identifier = "extraCompact";
//	        }
//	        Map<String,String> url2 = (Map<String,String>)big.get("url");
//	        if(url2 == null) {
//	        	return;
//	        }
//	        this.bigPictureUrl = url2.get(identifier);
//	    }

	}

	private List<EdekaRecipe> recipes = new ArrayList<EdekaRecipe>();
	private int totalCount;
	private int currentPage;
	private int currentSize;
}

package com.jimzrt.RezeptMeister.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@Table(indexes = { @Index(name = "title_index", columnList = "title"), @Index(name = "difficulty_index", columnList = "difficulty") })
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private @NonNull String title;
	
	private @NonNull String seoTitle;
	
	@Column(columnDefinition="TEXT")
	private String description = "";
	
	private String pictureUrl = "";

	@Enumerated(EnumType.STRING)
	private @NonNull Difficulty difficulty;

	private int defaultServingSize = 1;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "nutrition_id", referencedColumnName = "id")
	private @NonNull Nutrition nutrition;

	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	private @NonNull List<IngredientGroup> ingredientGroups = new ArrayList<IngredientGroup>();
	

	@ElementCollection(fetch = FetchType.LAZY)
	private List<String> directions = new ArrayList<String>();

	private int preperationTime;
	private int totalTime;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "Recipe_Ingredient", joinColumns = { @JoinColumn(name = "recipe_id") }, inverseJoinColumns = {
			@JoinColumn(name = "ingredient_id") })
	private @NonNull Set<Ingredient> ingredients = new HashSet<Ingredient>();

	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "Recipe_Tag", joinColumns = { @JoinColumn(name = "recipe_id") }, inverseJoinColumns = {
			@JoinColumn(name = "tag_id") })
	private Set<Tag> tags;
	
	public void addAmountGroup(IngredientGroup ingredientGroup) {
		this.ingredientGroups.add(ingredientGroup);
		ingredientGroup.setRecipe(this);
		this.ingredients.addAll(ingredientGroup.getAmounts().keySet());
	}
	
	public void setNutrition(@NonNull Nutrition nutrition) {
		nutrition.setRecipe(this);
		this.nutrition = nutrition;
	}
	
//	public void setAmountGroups(@NonNull List<IngredientGroup> ingredientGroups) {
//		this.amountGroups = ingredientGroups;
//		ingredientGroups.stream().forEach(ag -> ag.setRecipe(this));
//		this.ingredients = ingredientGroups.stream().flatMap(ag -> ag.getAmounts().keySet().stream()).collect(Collectors.toSet());
//	}

}

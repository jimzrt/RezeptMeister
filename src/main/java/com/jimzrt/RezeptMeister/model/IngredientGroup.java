package com.jimzrt.RezeptMeister.model;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Data
public class IngredientGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6308841313251268260L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "recipe_id", nullable = false)
	@ToString.Exclude
	@JsonIgnore
	@EqualsAndHashCode.Exclude
	private Recipe recipe;

	private @NonNull String name;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "amountgroup_amounts_mapping", joinColumns = {
			@JoinColumn(name = "amountgroup_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "amount_id", referencedColumnName = "id") })
	@MapKeyJoinColumn(name = "ingredient_id")
	@JsonSerialize(keyUsing = IngredientInMapSerializer.class)
	private @NonNull Map<Ingredient, Amount> amounts;

}

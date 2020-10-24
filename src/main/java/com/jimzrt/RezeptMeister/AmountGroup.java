package com.jimzrt.RezeptMeister;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Tuple;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;

import lombok.Data;
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
public class AmountGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6308841313251268260L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    @ManyToOne
    @JoinColumn(name="recipe_id", nullable=false)
    @ToString.Exclude
    @JsonIgnore 
    private Recipe recipe;

	private @NonNull String name;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "amountgroup_amounts_mapping", joinColumns = {
			@JoinColumn(name = "amountgroup_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "amount_id", referencedColumnName = "id") })
	@MapKeyJoinColumn(name="ingredient_id")
	private @NonNull Map<Ingredient, Amount> amounts;

}

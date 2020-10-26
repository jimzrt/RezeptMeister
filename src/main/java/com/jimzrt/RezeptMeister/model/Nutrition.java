package com.jimzrt.RezeptMeister.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@RequiredArgsConstructor @Getter @Setter @NoArgsConstructor @Data
public class Nutrition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3132918089470472428L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(mappedBy = "nutrition")
	@ToString.Exclude
	@JsonIgnore 
    private Recipe recipe;

	private @NonNull Float kcal;
	private @NonNull Float kj;
	private @NonNull Float carohydrates;
	private @NonNull Float protein;
	private @NonNull Float fat;
	private @NonNull Float cholesterol;
	private @NonNull Float roughage;
}

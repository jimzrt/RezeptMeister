package com.jimzrt.RezeptMeister;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor @Getter @Setter @Data
public class Tag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4552364451491732741L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private @NonNull String name;
	
	@ManyToMany(mappedBy = "tags")
    private Set<Recipe> recipes = new HashSet<>();
}

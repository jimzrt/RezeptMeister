package com.jimzrt.RezeptMeister.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @RequiredArgsConstructor @Data
@Table(indexes = { @Index(name = "name_index", columnList = "name") })
public class Ingredient implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4592803989399511516L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String pictureUrl;
	
	@Column(unique = true)
	private @NonNull String name;
	
	

}

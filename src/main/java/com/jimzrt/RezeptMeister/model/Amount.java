package com.jimzrt.RezeptMeister.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor @NoArgsConstructor @Getter @Setter @Data
public class Amount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8356339035754852531L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private @NonNull Unit unit;
	
	private @NonNull Float quantity;

}

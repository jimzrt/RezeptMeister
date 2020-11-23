package com.jimzrt.RezeptMeister.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Data
public class Unit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4648361826583105588L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private @NonNull String name;

}

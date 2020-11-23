package com.jimzrt.RezeptMeister.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(indexes = { @Index(name = "tag_name_index", columnList = "name") })
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Tag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4552364451491732741L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private @NonNull String name;

	@ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
	@JsonIgnore
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Recipe> recipes = new HashSet<>();
}

package com.jimzrt.RezeptMeister.model;

import java.io.Serializable;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

import cz.jirutka.unidecode.Unidecode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Indexed
@Table(indexes = { @Index(name = "name_index", columnList = "name") })
public class Ingredient implements Serializable {

	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]+");

	/**
	 * 
	 */
	private static final long serialVersionUID = 4592803989399511516L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	@Field(termVector = TermVector.YES)
	private @NonNull String name;

	private @NonNull String slug;

	private String pictureUrl;

	public static String makeSlugForName(String ingredientName) {

		Unidecode unidecode = Unidecode.toAscii();
		var slug = ingredientName.toLowerCase().trim();
		slug = unidecode.decode(slug);
		slug = WHITESPACE.matcher(slug).replaceAll("-");
		slug = Normalizer.normalize(slug, Form.NFD);
		slug = NONLATIN.matcher(slug).replaceAll("");
		return slug;
	}

}

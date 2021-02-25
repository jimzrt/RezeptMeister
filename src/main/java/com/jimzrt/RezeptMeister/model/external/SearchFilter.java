package com.jimzrt.RezeptMeister.model.external;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SearchFilter {
	@JsonIgnoreProperties(ignoreUnknown = true)
	@Data
	public static class Exclude {
		private List<Long> ingredient = new ArrayList<Long>();
		private List<String> ingredient_special = new ArrayList<String>();
		private List<Long> tag = new ArrayList<Long>();
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@Data
	public static class Entry {
		private String id = "";
		private Boolean exclude = false;
		private Boolean wildcard = false;
//		private List<String> ingredient_special = new ArrayList<String>();
//		private List<Long> tag = new ArrayList<Long>();
	}

	private int count = 12;
	private int page = 0;
	private int calories = 0;
	private int totalTime = 0;
	private List<Entry> ingredient = new ArrayList<Entry>();
	// private List<String> ingredient_special = new ArrayList<String>();

	private List<Long> recipe = new ArrayList<Long>();
	private List<Entry> tag = new ArrayList<Entry>();
	private List<String> difficulty = new ArrayList<String>();
//	private Exclude exclude = new Exclude();
}

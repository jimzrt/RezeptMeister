package com.jimzrt.RezeptMeister.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class IngredientInMapSerializer extends JsonSerializer<Ingredient> {

	@Override
	public void serialize(Ingredient value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeFieldName(value.getName());

	}

}

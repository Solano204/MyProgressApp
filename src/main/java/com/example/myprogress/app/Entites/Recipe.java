package com.example.myprogress.app.Entites;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Document("Recipes")
@Data
public class Recipe implements Serializable {

        @Hidden
        @Id
        ObjectId id;

        @Field(name = "nameRecipe")
        @Schema(description = "Name of the recipe", example = "Spaghetti Bolognese", required = true)
        private String nameRecipe;

        @Field(name = "user")
        @Schema(description = "User who created the recipe", example = "younowjs2", required = true)
        private String user;

        @Field(name = "Utensils")
        @Schema(description = "List of utensils required for the recipe", example = "[\"pot\", \"pan\", \"spoon\"]")
        private List<String> utensils;

        @Field(name = "Ingredients")
        @Schema(description = "Ingredients needed for the recipe", example = "{ \"Tomato\": \"2 cups\", \"Onion\": \"1 cup\" }")
        private Map<String, String> ingredients;

        @Field(name = "Steps")
        @Schema(description = "Steps to prepare the recipe", example = "{ \"Step 1\": \"Chop the onions.\", \"Step 2\": \"Heat the oil in a pan.\" }")
        private Map<String, String> steps;

        @Field(name = "AproximateTime")
        @Schema(description = "Approximate time needed to prepare the recipe", example = "30 minutes", allowableValues = {
                        "0 a 30 minutos", "30 minutos a 1 hora",
                        "1 a 1 hora y 30 minutos", "1 y 30 minutos a 2 hora",
                        "2 a 3 horas" })
        private String timeDuration;

        @Field(name = "anyRecomendation")
        @Schema(description = "Additional recommendations for the recipe", example = "Serve with garlic bread.")
        private String anyRecomendation;
}

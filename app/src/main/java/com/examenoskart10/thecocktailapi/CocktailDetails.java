package com.examenoskart10.thecocktailapi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CocktailDetails {

    @SerializedName("drinks")
    private List<DrinkDetail> drinks;

    public String getStrDrink() {
        return drinks != null && !drinks.isEmpty() ? drinks.get(0).strDrink : "Desconocido";
    }

    public String getStrInstructions() {
        return drinks != null && !drinks.isEmpty() ? drinks.get(0).strInstructions : "No hay instrucciones disponibles";
    }

    public String getIngredients() {
        if (drinks != null && !drinks.isEmpty()) {
            DrinkDetail detail = drinks.get(0);
            List<String> ingredients = new ArrayList<>();
            if (detail.strIngredient1 != null) ingredients.add(detail.strIngredient1);
            if (detail.strIngredient2 != null) ingredients.add(detail.strIngredient2);
            if (detail.strIngredient3 != null) ingredients.add(detail.strIngredient3);
            if (detail.strIngredient4 != null) ingredients.add(detail.strIngredient4);

            return String.join(", ", ingredients);
        }
        return "No hay ingredientes disponibles";
    }

    public static class DrinkDetail {
        @SerializedName("strDrink")
        public String strDrink;

        @SerializedName("strInstructions")
        public String strInstructions;

        @SerializedName("strIngredient1")
        public String strIngredient1;

        @SerializedName("strIngredient2")
        public String strIngredient2;

        @SerializedName("strIngredient3")
        public String strIngredient3;

        @SerializedName("strIngredient4")
        public String strIngredient4;


    }
}


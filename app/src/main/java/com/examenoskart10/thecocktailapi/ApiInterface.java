package com.examenoskart10.thecocktailapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("filter.php")
    Call<com.examenoskart10.thecocktailapi.Drinks> getDrinksByLicour(@Query("i") String licour);
    @GET("lookup.php")
    Call<com.examenoskart10.thecocktailapi.CocktailDetails> getCocktailDetailsById(@Query("i") String cocktailId);
}

package com.examenoskart10.thecocktailapi;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText searchInput;
    private RecyclerView recyclerView;
    private com.examenoskart10.thecocktailapi.CocktailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchInput = findViewById(R.id.search_input);
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        fetchCocktails("Gin");

        searchInput.setOnEditorActionListener((v, actionId, event) -> {
            String ingredient = searchInput.getText().toString().trim();
            if (!ingredient.isEmpty()) {
                fetchCocktails(ingredient);
            } else {
                Toast.makeText(MainActivity.this, "Introduce un ingrediente", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }


    private void fetchCocktails(String ingredient) {
        com.examenoskart10.thecocktailapi.ApiInterface apiInterface = com.examenoskart10.thecocktailapi.ApiClient.getClient().create(com.examenoskart10.thecocktailapi.ApiInterface.class);
        Call<com.examenoskart10.thecocktailapi.Drinks> call = apiInterface.getDrinksByLicour(ingredient);

        call.enqueue(new Callback<com.examenoskart10.thecocktailapi.Drinks>() {
            @Override
            public void onResponse(Call<com.examenoskart10.thecocktailapi.Drinks> call, Response<Drinks> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<com.examenoskart10.thecocktailapi.Drinks.Coctail> cocktailList = response.body().drinks;
                    if (cocktailList != null && !cocktailList.isEmpty()) {
                        adapter = new com.examenoskart10.thecocktailapi.CocktailAdapter(MainActivity.this, cocktailList, cocktail -> {
                            // Mostrar detalles del cóctel al hacer clic en uno
                            showCocktailDetails(cocktail);
                        });
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(MainActivity.this, "No se encontraron cócteles", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.examenoskart10.thecocktailapi.Drinks> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCocktailDetails(Drinks.Coctail cocktail) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CocktailDetails> call = apiInterface.getCocktailDetailsById(cocktail.coctailId);

        call.enqueue(new Callback<CocktailDetails>() {
            @Override
            public void onResponse(Call<CocktailDetails> call, Response<CocktailDetails> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CocktailDetails details = response.body();

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle(details.getStrDrink())
                            .setMessage(
                                    "Instrucciones: " + details.getStrInstructions() +
                                            "\n\nIngredientes: " + details.getIngredients()
                            )
                            .setPositiveButton("OK", null)
                            .show();
                } else {
                    Toast.makeText(MainActivity.this, "Error al cargar detalles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CocktailDetails> call, Throwable t) {
                Log.e("API_DETAILS_ERROR", t.getMessage());
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.examenoskart10.thecocktailapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.List;

public class CocktailAdapter extends RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder> {
    private Context context;
    private List<com.examenoskart10.thecocktailapi.Drinks.Coctail> cocktailList;
    private OnCocktailClickListener listener;

    public CocktailAdapter(Context context, List<com.examenoskart10.thecocktailapi.Drinks.Coctail> cocktailList, OnCocktailClickListener listener) {
        this.context = context;
        this.cocktailList = cocktailList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cocktail, parent, false);
        return new CocktailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailViewHolder holder, int position) {
        com.examenoskart10.thecocktailapi.Drinks.Coctail cocktail = cocktailList.get(position);
        holder.cocktailName.setText(cocktail.coctailName);

        if (cocktail.coctailImageUrl != null && !cocktail.coctailImageUrl.isEmpty()) {
            Picasso.get()
                    .load(cocktail.coctailImageUrl).fit().centerCrop().into(holder.cocktailImage);
        } else {
            holder.cocktailImage.setImageDrawable(null);
        }

        holder.itemView.setOnClickListener(v -> listener.onCocktailClick(cocktail));
    }



    @Override
    public int getItemCount() {
        return cocktailList.size();
    }

    static class CocktailViewHolder extends RecyclerView.ViewHolder {
        TextView cocktailName;
        ImageView cocktailImage;

        public CocktailViewHolder(@NonNull View itemView) {
            super(itemView);
            cocktailName = itemView.findViewById(R.id.cocktail_name);
            cocktailImage = itemView.findViewById(R.id.cocktail_image);
        }
    }

    public interface OnCocktailClickListener {
        void onCocktailClick(com.examenoskart10.thecocktailapi.Drinks.Coctail cocktail);
    }
}

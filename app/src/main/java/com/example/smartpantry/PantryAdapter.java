package com.example.smartpantry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PantryAdapter extends BaseAdapter {
    private final Context context;
    private final List<Ingredient> ingredients;

    public PantryAdapter(Context context, List<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Ingredient getItem(int position) {
        return ingredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.row_pantry_item, parent, false);
        }
        Ingredient ingredient = getItem(position);
        TextView name = view.findViewById(R.id.itemName);
        TextView details = view.findViewById(R.id.itemDetails);
        name.setText(ingredient.name);
        String expiry = ingredient.expiryDate == null || ingredient.expiryDate.isEmpty() ? "No expiry date" : "Expires: " + ingredient.expiryDate;
        details.setText(formatQuantity(ingredient.quantity) + " " + ingredient.unit + " - " + expiry);
        return view;
    }

    private String formatQuantity(double quantity) {
        if (quantity == Math.rint(quantity)) {
            return String.valueOf((int) quantity);
        }
        return String.valueOf(quantity);
    }
}

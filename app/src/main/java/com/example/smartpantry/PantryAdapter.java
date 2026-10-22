package com.example.smartpantry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Calendar;
import java.util.Date;

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
        android.content.SharedPreferences prefs = context.getSharedPreferences("smart_pantry_settings", Context.MODE_PRIVATE);
        String warning = "";
        Date date = IngredientRules.parseDate(ingredient.expiryDate);
        if (prefs.getBoolean("expiry_alerts", true) && date != null) {
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0); today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0); today.set(Calendar.MILLISECOND, 0);
            Calendar soon = (Calendar) today.clone(); soon.add(Calendar.DAY_OF_YEAR, 3);
            if (date.before(today.getTime())) warning = " - Expired";
            else if (!date.after(soon.getTime())) warning = " - Expiring soon";
        }
        boolean kitchen = "simple".equals(prefs.getString("units", "metric"));
        details.setText(IngredientRules.quantity(ingredient.quantity, ingredient.unit, kitchen) + " - " + expiry + warning);
        details.setTextColor(warning.isEmpty() ? 0xff333333 : 0xffa43631);
        return view;
    }

}

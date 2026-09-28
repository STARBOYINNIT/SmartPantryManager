package com.example.smartpantry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity {
    private DatabaseHelper databaseHelper;
    private ListView pantryList;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        pantryList = findViewById(R.id.pantryList);
        emptyText = findViewById(R.id.emptyText);
        Button addButton = findViewById(R.id.addButton);
        Button suggestedButton = findViewById(R.id.suggestedButton);
        Button settingsButton = findViewById(R.id.settingsButton);

        addButton.setOnClickListener(v -> startActivity(new Intent(this, IngredientFormActivity.class)));
        suggestedButton.setOnClickListener(v -> startActivity(new Intent(this, SuggestedRecipesActivity.class)));
        settingsButton.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
        pantryList.setOnItemClickListener((parent, view, position, id) -> {
            Ingredient ingredient = (Ingredient) parent.getItemAtPosition(position);
            Intent intent = new Intent(this, IngredientFormActivity.class);
            intent.putExtra("ingredient_id", ingredient.id);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantry();
    }

    private void loadPantry() {
        List<Ingredient> ingredients = databaseHelper.getAllIngredients();
        pantryList.setAdapter(new PantryAdapter(this, ingredients));
        emptyText.setVisibility(ingredients.isEmpty() ? View.VISIBLE : View.GONE);
    }
}

package com.example.smartpantry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class SuggestedRecipesActivity extends NavigationActivity {
    private DatabaseHelper databaseHelper;
    private ListView recipeList;
    private TextView noRecipesText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);
        databaseHelper = new DatabaseHelper(this);
        recipeList = findViewById(R.id.recipeList);
        noRecipesText = findViewById(R.id.noRecipesText);

        recipeList.setOnItemClickListener((parent, view, position, id) -> {
            Recipe recipe = (Recipe) parent.getItemAtPosition(position);
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra("recipe_id", recipe.id);
            startActivity(intent);
        });
    }

    @Override protected void onResume() {
        super.onResume();
        List<Recipe> recipes = databaseHelper.getSuggestedRecipes();
        recipeList.setAdapter(new RecipeAdapter(this, recipes));
        noRecipesText.setVisibility(recipes.isEmpty() ? View.VISIBLE : View.GONE);
    }
}

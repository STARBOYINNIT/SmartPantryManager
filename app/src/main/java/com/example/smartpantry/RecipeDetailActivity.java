package com.example.smartpantry;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class RecipeDetailActivity extends NavigationActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        TextView recipeName = findViewById(R.id.recipeName);
        TextView ingredientsText = findViewById(R.id.ingredientsText);
        TextView stepsText = findViewById(R.id.stepsText);

        int recipeId = getIntent().getIntExtra("recipe_id", -1);
        Recipe recipe = new DatabaseHelper(this).getRecipe(recipeId);
        if (recipe == null) {
            recipeName.setText("Recipe not found");
            return;
        }

        recipeName.setText(recipe.name);
        StringBuilder ingredients = new StringBuilder("Ingredients\n");
        for (RecipeIngredient ingredient : recipe.ingredients) {
            ingredients.append("- ")
                    .append(formatQuantity(ingredient.quantity))
                    .append(" ")
                    .append(ingredient.unit)
                    .append(" ")
                    .append(ingredient.name)
                    .append("\n");
        }
        ingredientsText.setText(ingredients.toString());
        stepsText.setText("Method\n" + recipe.steps);
    }

    private String formatQuantity(double quantity) {
        if (quantity == Math.rint(quantity)) {
            return String.valueOf((int) quantity);
        }
        return String.valueOf(quantity);
    }
}

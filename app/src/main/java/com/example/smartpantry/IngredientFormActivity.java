package com.example.smartpantry;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class IngredientFormActivity extends Activity {
    private DatabaseHelper databaseHelper;
    private EditText nameInput;
    private EditText quantityInput;
    private EditText unitInput;
    private EditText expiryInput;
    private int ingredientId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_form);
        databaseHelper = new DatabaseHelper(this);
        nameInput = findViewById(R.id.nameInput);
        quantityInput = findViewById(R.id.quantityInput);
        unitInput = findViewById(R.id.unitInput);
        expiryInput = findViewById(R.id.expiryInput);
        TextView formTitle = findViewById(R.id.formTitle);
        Button saveButton = findViewById(R.id.saveButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        ingredientId = getIntent().getIntExtra("ingredient_id", -1);
        if (ingredientId != -1) {
            formTitle.setText("Edit Ingredient");
            deleteButton.setVisibility(View.VISIBLE);
            Ingredient ingredient = databaseHelper.getIngredient(ingredientId);
            if (ingredient != null) {
                nameInput.setText(ingredient.name);
                quantityInput.setText(String.valueOf(ingredient.quantity));
                unitInput.setText(ingredient.unit);
                expiryInput.setText(ingredient.expiryDate);
            }
        }

        saveButton.setOnClickListener(v -> saveIngredient());
        deleteButton.setOnClickListener(v -> confirmDelete());
    }

    private void saveIngredient() {
        String name = nameInput.getText().toString().trim();
        String quantityText = quantityInput.getText().toString().trim();
        String unit = unitInput.getText().toString().trim();
        String expiry = expiryInput.getText().toString().trim();

        if (name.isEmpty()) {
            nameInput.setError("Name is required");
            return;
        }
        if (quantityText.isEmpty()) {
            quantityInput.setError("Quantity is required");
            return;
        }
        if (unit.isEmpty()) {
            unitInput.setError("Unit is required");
            return;
        }

        double quantity;
        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException ex) {
            quantityInput.setError("Enter a valid number");
            return;
        }

        if (quantity <= 0) {
            quantityInput.setError("Quantity must be greater than zero");
            return;
        }

        if (ingredientId == -1) {
            databaseHelper.addIngredient(name, quantity, unit, expiry);
            Toast.makeText(this, "Ingredient added", Toast.LENGTH_SHORT).show();
        } else {
            databaseHelper.updateIngredient(ingredientId, name, quantity, unit, expiry);
            Toast.makeText(this, "Ingredient updated", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete ingredient?")
                .setMessage("This will remove the ingredient from your pantry.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    databaseHelper.deleteIngredient(ingredientId);
                    Toast.makeText(this, "Ingredient deleted", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}

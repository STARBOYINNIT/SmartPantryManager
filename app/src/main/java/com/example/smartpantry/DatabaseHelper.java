package com.example.smartpantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "smart_pantry.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pantry_items (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT NOT NULL, expiry_date TEXT)");
        db.execSQL("CREATE TABLE recipes (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, steps TEXT NOT NULL)");
        db.execSQL("CREATE TABLE recipe_ingredients (id INTEGER PRIMARY KEY AUTOINCREMENT, recipe_id INTEGER NOT NULL, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT NOT NULL)");
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipe_ingredients");
        db.execSQL("DROP TABLE IF EXISTS recipes");
        db.execSQL("DROP TABLE IF EXISTS pantry_items");
        onCreate(db);
    }

    public long addIngredient(String name, double quantity, String unit, String expiryDate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = ingredientValues(name, quantity, unit, expiryDate);
        return db.insert("pantry_items", null, values);
    }

    public int updateIngredient(int id, String name, double quantity, String unit, String expiryDate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = ingredientValues(name, quantity, unit, expiryDate);
        return db.update("pantry_items", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public int deleteIngredient(int id) {
        return getWritableDatabase().delete("pantry_items", "id = ?", new String[]{String.valueOf(id)});
    }

    public Ingredient getIngredient(int id) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT id, name, quantity, unit, expiry_date FROM pantry_items WHERE id = ?", new String[]{String.valueOf(id)});
        try {
            if (cursor.moveToFirst()) {
                return readIngredient(cursor);
            }
            return null;
        } finally {
            cursor.close();
        }
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT id, name, quantity, unit, expiry_date FROM pantry_items ORDER BY name", null);
        try {
            while (cursor.moveToNext()) {
                ingredients.add(readIngredient(cursor));
            }
        } finally {
            cursor.close();
        }
        return ingredients;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT id, name, steps FROM recipes ORDER BY name", null);
        try {
            while (cursor.moveToNext()) {
                Recipe recipe = new Recipe(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                recipe.ingredients = getRecipeIngredients(recipe.id);
                recipes.add(recipe);
            }
        } finally {
            cursor.close();
        }
        return recipes;
    }

    public Recipe getRecipe(int id) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT id, name, steps FROM recipes WHERE id = ?", new String[]{String.valueOf(id)});
        try {
            if (cursor.moveToFirst()) {
                Recipe recipe = new Recipe(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                recipe.ingredients = getRecipeIngredients(recipe.id);
                return recipe;
            }
            return null;
        } finally {
            cursor.close();
        }
    }

    public List<Recipe> getSuggestedRecipes() {
        List<Ingredient> pantry = getAllIngredients();
        List<Recipe> matches = new ArrayList<>();
        for (Recipe recipe : getAllRecipes()) {
            if (MatchUtils.canMakeRecipe(recipe, pantry)) {
                matches.add(recipe);
            }
        }
        return matches;
    }

    private ContentValues ingredientValues(String name, double quantity, String unit, String expiryDate) {
        ContentValues values = new ContentValues();
        values.put("name", name.trim());
        values.put("quantity", quantity);
        values.put("unit", unit.trim());
        values.put("expiry_date", expiryDate == null ? "" : expiryDate.trim());
        return values;
    }

    private Ingredient readIngredient(Cursor cursor) {
        return new Ingredient(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4));
    }

    private List<RecipeIngredient> getRecipeIngredients(int recipeId) {
        List<RecipeIngredient> ingredients = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT name, quantity, unit FROM recipe_ingredients WHERE recipe_id = ? ORDER BY id", new String[]{String.valueOf(recipeId)});
        try {
            while (cursor.moveToNext()) {
                ingredients.add(new RecipeIngredient(cursor.getString(0), cursor.getDouble(1), cursor.getString(2)));
            }
        } finally {
            cursor.close();
        }
        return ingredients;
    }

    private void seedRecipes(SQLiteDatabase db) {
        addRecipe(db, "Tomato Egg Scramble", "Whisk eggs. Cook chopped tomato in a pan. Add eggs and stir until set.", new Object[][]{{"egg", 2, "item"}, {"tomato", 1, "item"}});
        addRecipe(db, "Banana Oats", "Cook oats with milk. Slice banana on top and serve warm.", new Object[][]{{"oats", 50, "g"}, {"milk", 200, "ml"}, {"banana", 1, "item"}});
        addRecipe(db, "Cheese Toast", "Butter bread, add cheese, and toast until golden.", new Object[][]{{"bread", 2, "item"}, {"cheese", 40, "g"}, {"butter", 10, "g"}});
        addRecipe(db, "Rice and Beans", "Warm cooked rice and beans together. Season and serve.", new Object[][]{{"rice", 150, "g"}, {"beans", 120, "g"}});
        addRecipe(db, "Tuna Pasta", "Boil pasta. Stir through tuna and tomato sauce.", new Object[][]{{"pasta", 100, "g"}, {"tuna", 1, "item"}, {"tomato sauce", 80, "ml"}});
        addRecipe(db, "Chicken Salad", "Slice cooked chicken and mix with lettuce, tomato, and cucumber.", new Object[][]{{"chicken", 120, "g"}, {"lettuce", 50, "g"}, {"tomato", 1, "item"}, {"cucumber", 1, "item"}});
        addRecipe(db, "Peanut Butter Sandwich", "Spread peanut butter on bread and close sandwich.", new Object[][]{{"bread", 2, "item"}, {"peanut butter", 30, "g"}});
        addRecipe(db, "Vegetable Soup", "Simmer carrot, potato, onion, and stock until soft.", new Object[][]{{"carrot", 2, "item"}, {"potato", 2, "item"}, {"onion", 1, "item"}, {"stock", 500, "ml"}});
        addRecipe(db, "Fruit Yogurt Bowl", "Add chopped fruit to yogurt and serve chilled.", new Object[][]{{"yogurt", 150, "g"}, {"banana", 1, "item"}, {"apple", 1, "item"}});
        addRecipe(db, "Garlic Butter Pasta", "Boil pasta. Melt butter with garlic and toss together.", new Object[][]{{"pasta", 100, "g"}, {"butter", 20, "g"}, {"garlic", 2, "item"}});
        addRecipe(db, "Potato Omelette", "Cook sliced potato, add beaten eggs, and cook until firm.", new Object[][]{{"potato", 1, "item"}, {"egg", 2, "item"}});
        addRecipe(db, "Apple Cinnamon Oats", "Cook oats with milk, apple, and cinnamon.", new Object[][]{{"oats", 50, "g"}, {"milk", 200, "ml"}, {"apple", 1, "item"}, {"cinnamon", 1, "tsp"}});
        addRecipe(db, "Tomato Rice", "Cook rice with tomato sauce and onion until warm.", new Object[][]{{"rice", 150, "g"}, {"tomato sauce", 100, "ml"}, {"onion", 1, "item"}});
        addRecipe(db, "Egg Fried Rice", "Fry rice with egg and peas until hot.", new Object[][]{{"rice", 150, "g"}, {"egg", 1, "item"}, {"peas", 50, "g"}});
        addRecipe(db, "Cucumber Tomato Salad", "Chop cucumber and tomato. Toss with oil and serve.", new Object[][]{{"cucumber", 1, "item"}, {"tomato", 1, "item"}, {"oil", 1, "tbsp"}});
        addRecipe(db, "Simple Pancakes", "Mix flour, milk, and egg. Fry spoonfuls until golden.", new Object[][]{{"flour", 120, "g"}, {"milk", 200, "ml"}, {"egg", 1, "item"}});
        addRecipe(db, "Mashed Potatoes", "Boil potatoes, mash with milk and butter.", new Object[][]{{"potato", 3, "item"}, {"milk", 50, "ml"}, {"butter", 20, "g"}});
        addRecipe(db, "Bean Toast", "Toast bread and top with warmed beans.", new Object[][]{{"bread", 2, "item"}, {"beans", 120, "g"}});
        addRecipe(db, "Chicken Rice Bowl", "Serve warm chicken over rice with peas.", new Object[][]{{"chicken", 120, "g"}, {"rice", 150, "g"}, {"peas", 50, "g"}});
        addRecipe(db, "Yogurt Smoothie", "Blend yogurt, milk, and banana until smooth.", new Object[][]{{"yogurt", 120, "g"}, {"milk", 100, "ml"}, {"banana", 1, "item"}});
    }

    private void addRecipe(SQLiteDatabase db, String name, String steps, Object[][] ingredients) {
        ContentValues recipeValues = new ContentValues();
        recipeValues.put("name", name);
        recipeValues.put("steps", steps);
        long recipeId = db.insert("recipes", null, recipeValues);
        for (Object[] ingredient : ingredients) {
            ContentValues values = new ContentValues();
            values.put("recipe_id", recipeId);
            values.put("name", (String) ingredient[0]);
            values.put("quantity", ((Number) ingredient[1]).doubleValue());
            values.put("unit", (String) ingredient[2]);
            db.insert("recipe_ingredients", null, values);
        }
    }
}

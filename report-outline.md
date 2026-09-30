# Smart Pantry Manager Report Draft

## Cover Page

App name: Smart Pantry Manager  
Student name:  
Student number:  
Module: Mobile App Development 700  
Date:

## Table of Contents

Add after the final report is written.

## 1. Introduction

Smart Pantry Manager helps home users reduce food waste by tracking leftover ingredients and suggesting only recipes that can be cooked immediately with the ingredients already available.

The target user is a student, family member, or home cook who wants to avoid buying unnecessary ingredients and use food before it expires. The app focuses on pantry tracking, local storage, and strict recipe matching.

## 2. System Design

### Screen Flow

Pantry List -> Add/Edit Ingredient  
Pantry List -> Suggested Recipes -> Recipe Detail  
Pantry List -> Settings

Explanation:

- The Pantry List is the home screen.
- The Add/Edit Ingredient screen is used for CRUD operations.
- The Suggested Recipes screen uses the strict matching algorithm.
- The Recipe Detail screen shows the selected recipe method.
- The Settings screen stores simple user preferences.

### Data Model

- pantry_items: id, name, quantity, unit, expiry_date
- recipes: id, name, steps
- recipe_ingredients: id, recipe_id, name, quantity, unit

Relationship:

- One recipe has many recipe_ingredients.
- Pantry items are compared against recipe_ingredients to decide whether a recipe can be suggested.

## 3. Screenshots of Every Output

Use the screenshots saved in `outputs/smart-pantry-screenshots`.

- `01-empty-pantry.png`: Empty pantry list shown when no ingredients are stored.
- `02-add-ingredient-form.png`: Form for adding a new ingredient.
- `03-validation-error.png`: Required-field validation on the ingredient form.
- `04-pantry-with-tomato.png`: Pantry list after adding tomato.
- `05-no-recipe-match.png`: Suggested Recipes screen when the pantry does not satisfy any full recipe.
- `06-pantry-with-tomato-and-eggs.png`: Pantry list with enough ingredients for Tomato Egg Scramble.
- `07-suggested-recipes-match.png`: Strict match result showing Tomato Egg Scramble.
- `08-recipe-detail.png`: Recipe detail screen with ingredients and method.
- `09-settings-screen.png`: Settings screen.
- `10-settings-simple-units-selected.png`: Settings preference changed.
- `11-edit-ingredient-form.png`: Edit screen for an existing pantry item.
- `12-pantry-after-edit.png`: Pantry list after an update.
- `13-pantry-after-delete.png`: Pantry list after deletion.

## 4. Key Code Snippets

Suggested snippets:

- `DatabaseHelper.onCreate`: creates SQLite tables and seeds recipe data.
- `DatabaseHelper.addIngredient`, `updateIngredient`, and `deleteIngredient`: show CRUD.
- `MatchUtils.canMakeRecipe`: shows the strict matching rule.
- `PantryAdapter.getView`: shows how dynamic pantry list rows are displayed.
- Intent navigation from `MainActivity`: shows movement between screens.

## 5. Challenges and Solutions

- Challenge 1: Strict matching could fail on small text differences such as tomato/tomatoes.
  Solution: ingredient names are normalized before comparison.
- Challenge 2: Recipe matching needed to consider quantity, not only ingredient name.
  Solution: the app compares converted base quantities before suggesting recipes.
- Challenge 3: User data needed to remain after closing the app.
  Solution: SQLite stores pantry items locally on the device.

## 6. Conclusion and Reflection

This project improved understanding of Android Activities, Intents, SQLite persistence, list adapters, input validation, and business logic testing. With more time, the app could include expiring-soon notifications, recipe categories, and an optional separate Almost There recipe list.

## 7. Reference List

Suggested references to format in Harvard style:

- Android Developers. Activity lifecycle documentation.
- Android Developers. SQLiteOpenHelper documentation.
- Android Developers. ListView and Adapter documentation.
- Android Developers. Intents and Intent Filters documentation.

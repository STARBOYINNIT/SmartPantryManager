# Smart Pantry Manager Report Outline

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

## 2. System Design

### Screen Flow

Pantry List -> Add/Edit Ingredient  
Pantry List -> Suggested Recipes -> Recipe Detail  
Pantry List -> Settings

### Data Model

- pantry_items: id, name, quantity, unit, expiry_date
- recipes: id, name, steps
- recipe_ingredients: id, recipe_id, name, quantity, unit

## 3. Screenshots of Every Output

Add screenshots of:

- Empty pantry list
- Add ingredient form
- Input validation error
- Pantry list with ingredients
- Edit ingredient form
- Suggested recipes with matching recipe
- Suggested recipes with no matches
- Recipe detail screen
- Settings screen
- Delete confirmation/result

## 4. Key Code Snippets

Suggested snippets:

- `DatabaseHelper.onCreate` and recipe seeding
- `addIngredient`, `updateIngredient`, and `deleteIngredient`
- `MatchUtils.canMakeRecipe`
- `PantryAdapter.getView`
- Intent navigation from `MainActivity`

## 5. Challenges and Solutions

Examples to complete after testing:

- Handling singular and plural ingredient names.
- Making recipe matching strict but not too fragile.
- Keeping data after the app closes by using SQLite.

## 6. Conclusion and Reflection

Explain what was learned about Android Activities, SQLite, adapters, Intents, and validation.

## 7. Reference List

Add Android developer documentation, SQLite references, and any tutorials actually used.

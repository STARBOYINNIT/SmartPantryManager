package com.example.smartpantry;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MatchUtils {
    public static boolean canMakeRecipe(Recipe recipe, List<Ingredient> pantry) {
        Map<String, Ingredient> pantryByName = new HashMap<>();
        for (Ingredient ingredient : pantry) {
            pantryByName.put(normalizeName(ingredient.name), ingredient);
        }

        // Strict matching: every required ingredient must be present in enough quantity.
        for (RecipeIngredient required : recipe.ingredients) {
            Ingredient available = pantryByName.get(normalizeName(required.name));
            if (available == null) {
                return false;
            }
            double availableQuantity = convertToBase(available.quantity, available.unit);
            double requiredQuantity = convertToBase(required.quantity, required.unit);
            if (!sameUnitFamily(available.unit, required.unit) || availableQuantity < requiredQuantity) {
                return false;
            }
        }
        return true;
    }

    public static String normalizeName(String value) {
        String name = value == null ? "" : value.toLowerCase(Locale.ROOT).trim();
        name = name.replaceAll("[^a-z0-9 ]", "");
        // Handles simple plural forms such as tomatoes/tomato and berries/berry.
        if (name.endsWith("ies")) {
            name = name.substring(0, name.length() - 3) + "y";
        } else if (name.endsWith("oes")) {
            name = name.substring(0, name.length() - 2);
        } else if (name.endsWith("s") && name.length() > 3) {
            name = name.substring(0, name.length() - 1);
        }
        return name;
    }

    private static boolean sameUnitFamily(String first, String second) {
        return unitFamily(first).equals(unitFamily(second));
    }

    private static String unitFamily(String unit) {
        String normalized = normalizeUnit(unit);
        if (normalized.equals("g") || normalized.equals("kg")) return "mass";
        if (normalized.equals("ml") || normalized.equals("l")) return "volume";
        return normalized;
    }

    private static double convertToBase(double quantity, String unit) {
        String normalized = normalizeUnit(unit);
        if (normalized.equals("kg") || normalized.equals("l")) {
            return quantity * 1000;
        }
        return quantity;
    }

    private static String normalizeUnit(String unit) {
        String value = unit == null ? "" : unit.toLowerCase(Locale.ROOT).trim();
        if (value.equals("grams") || value.equals("gram")) return "g";
        if (value.equals("kilograms") || value.equals("kilogram")) return "kg";
        if (value.equals("millilitres") || value.equals("milliliters") || value.equals("millilitre") || value.equals("milliliter")) return "ml";
        if (value.equals("litres") || value.equals("liters") || value.equals("litre") || value.equals("liter")) return "l";
        if (value.equals("items") || value.equals("piece") || value.equals("pieces")) return "item";
        if (value.equals("tablespoon") || value.equals("tablespoons")) return "tbsp";
        if (value.equals("teaspoon") || value.equals("teaspoons")) return "tsp";
        if (value.endsWith("s") && value.length() > 3) return value.substring(0, value.length() - 1);
        return value;
    }
}

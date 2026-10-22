package com.example.smartpantry;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MatchUtils {
    public static boolean canMakeRecipe(Recipe recipe, List<Ingredient> pantry) {
        if (recipe.ingredients.isEmpty()) return false;
        Map<String, Double> available = new HashMap<>();
        Map<String, Double> needed = new HashMap<>();
        for (Ingredient ingredient : pantry) {
            addTotal(available, ingredient.name, ingredient.quantity, ingredient.unit);
        }
        for (RecipeIngredient required : recipe.ingredients) {
            addTotal(needed, required.name, required.quantity, required.unit);
        }
        // Total both sides so repeated requirements cannot reuse the same stock.
        for (Map.Entry<String, Double> required : needed.entrySet()) {
            Double quantity = available.get(required.getKey());
            if (quantity == null || quantity + 1e-9 < required.getValue()) return false;
        }
        return true;
    }

    private static void addTotal(Map<String, Double> totals, String name, double quantity, String unit) {
        if (!IngredientRules.validQuantity(quantity)) {
            throw new IllegalArgumentException("Quantity must be finite and positive");
        }
        String key = normalizeName(name) + "|" + unitFamily(unit);
        Double previous = totals.get(key);
        totals.put(key, (previous == null ? 0 : previous) + convertToBase(quantity, unit));
    }

    public static String normalizeName(String value) {
        String name = value == null ? "" : value.toLowerCase(Locale.ROOT).trim();
        name = name.replaceAll("[^a-z0-9 ]", "").replaceAll("\\s+", " ");
        if (name.equals("yoghurt")) return "yogurt";
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

    private static String unitFamily(String unit) {
        String normalized = normalizeUnit(unit);
        if (normalized.equals("g") || normalized.equals("kg")) return "mass";
        if (normalized.equals("ml") || normalized.equals("l") || normalized.equals("tbsp") || normalized.equals("tsp")) return "volume";
        return normalized;
    }

    private static double convertToBase(double quantity, String unit) {
        String normalized = normalizeUnit(unit);
        if (normalized.equals("kg") || normalized.equals("l")) {
            return quantity * 1000;
        }
        if (normalized.equals("tbsp")) return quantity * 15;
        if (normalized.equals("tsp")) return quantity * 5;
        return quantity;
    }

    public static String normalizeUnit(String unit) {
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

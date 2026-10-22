package com.example.smartpantry;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class IngredientRules {
    private IngredientRules() { }

    public static boolean validQuantity(double value) {
        return !Double.isNaN(value) && !Double.isInfinite(value) && value > 0;
    }

    public static Date parseDate(String value) {
        if (value == null || !value.matches("\\d{4}-\\d{2}-\\d{2}")) return null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
        format.setLenient(false);
        ParsePosition position = new ParsePosition(0);
        Date result = format.parse(value, position);
        return position.getIndex() == value.length() ? result : null;
    }

    public static String quantity(double value, String unit, boolean kitchenUnits) {
        String normalized = MatchUtils.normalizeUnit(unit);
        if (normalized.equals("kg")) { value *= 1000; normalized = "g"; }
        if (normalized.equals("l")) { value *= 1000; normalized = "ml"; }
        if (normalized.equals("tbsp")) { value *= 15; normalized = "ml"; }
        if (normalized.equals("tsp")) { value *= 5; normalized = "ml"; }
        if (kitchenUnits && normalized.equals("ml")) {
            if (value >= 15 && Math.abs(value / 15 - Math.rint(value / 15)) < 1e-9) {
                value /= 15; normalized = "tbsp";
            } else if (value >= 5 && Math.abs(value / 5 - Math.rint(value / 5)) < 1e-9) {
                value /= 5; normalized = "tsp";
            }
        }
        return java.math.BigDecimal.valueOf(value).stripTrailingZeros().toPlainString() + " " + normalized;
    }
}

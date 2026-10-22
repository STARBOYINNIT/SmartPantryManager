import com.example.smartpantry.*;
import java.util.*;

public class RegressionChecks {
    private static int checks;
    private static void check(boolean value, String label) {
        if (!value) throw new AssertionError(label);
        checks++;
    }
    private static Ingredient stock(String name, double amount, String unit) {
        return new Ingredient(1, name, amount, unit, "");
    }
    private static Recipe meal(String name, double amount, String unit) {
        Recipe result = new Recipe(1, "Test", "Test");
        result.ingredients.add(new RecipeIngredient(name, amount, unit));
        return result;
    }
    public static void main(String[] args) {
        check(MatchUtils.canMakeRecipe(meal("tomato", 1, "item"), Arrays.asList(stock("tomatoes",1,"items"))), "plural alias");
        check(!MatchUtils.canMakeRecipe(meal("egg",2,"item"), Arrays.asList(stock("egg",1,"item"))), "shortfall");
        check(!MatchUtils.canMakeRecipe(meal("egg",1,"item"), Collections.emptyList()), "missing");
        check(MatchUtils.canMakeRecipe(meal("egg",2,"item"), Arrays.asList(stock("egg",1,"item"),stock("eggs",1,"item"))), "stock totals");
        Recipe repeated=meal("egg",2,"item"); repeated.ingredients.add(new RecipeIngredient("eggs",2,"item"));
        check(!MatchUtils.canMakeRecipe(repeated,Arrays.asList(stock("egg",3,"item"))), "requirements also total");
        check(MatchUtils.canMakeRecipe(meal("flour",500,"g"),Arrays.asList(stock("flour",.5,"kg"))), "mass conversion");
        check(MatchUtils.canMakeRecipe(meal("milk",500,"ml"),Arrays.asList(stock("milk",.5,"litres"))), "volume conversion");
        check(!MatchUtils.canMakeRecipe(meal("milk",50,"ml"),Arrays.asList(stock("milk",50,"g"))), "mass volume separated");
        check(MatchUtils.canMakeRecipe(meal("oil",1,"tbsp"),Arrays.asList(stock("oil",3,"tsp"))), "spoon conversion");
        check(MatchUtils.canMakeRecipe(meal(" tomato sauce ",20,"ml"),Arrays.asList(stock("TOMATO   SAUCE",20,"ml"))), "whitespace case");
        check(!MatchUtils.canMakeRecipe(new Recipe(1,"Empty",""),Collections.emptyList()), "empty recipe");
        check(!IngredientRules.validQuantity(Double.NaN), "NaN rejected");
        check(!IngredientRules.validQuantity(Double.POSITIVE_INFINITY), "infinity rejected");
        check(!IngredientRules.validQuantity(0), "zero rejected");
        check(!IngredientRules.validQuantity(-1), "negative rejected");
        check(IngredientRules.parseDate("2028-02-29")!=null, "leap date");
        check(IngredientRules.parseDate("2026-02-29")==null, "invalid date");
        check(IngredientRules.parseDate("2026-13-01")==null, "invalid month");
        check(IngredientRules.parseDate("2026-1-01")==null, "strict format");
        check(IngredientRules.quantity(1.5,"kg",false).equals("1500 g"), "metric display");
        check(IngredientRules.quantity(30,"ml",true).equals("2 tbsp"), "kitchen display");
        check(IngredientRules.quantity(12,"ml",true).equals("12 ml"), "no rounding into spoons");
        System.out.println(checks+" regression checks passed");
    }
}

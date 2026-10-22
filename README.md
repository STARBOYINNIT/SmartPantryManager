# Smart Pantry Manager

Smart Pantry Manager is a Java Android application for Mobile App Development 700. It helps users reduce food waste by tracking ingredients they already have and suggesting only recipes that can be cooked with the current pantry.

## Database Choice

This project uses SQLite through `SQLiteOpenHelper`. SQLite was chosen because it runs locally on the device, works offline, is covered in many Android persistence lessons, and is simple to demonstrate during the video.

## Core Features

- Add, view, edit, and delete pantry ingredients.
- Validate required ingredient fields and optional expiry dates.
- Store pantry data persistently in SQLite.
- Seed 20 recipes on first run.
- Suggest recipes only when every required ingredient exists in the pantry in the required quantity.
- Show recipe details with ingredients and preparation steps.
- Include a settings screen for expiring-soon alerts and unit preference.
- Use Java Activities, Intents, ListView, and custom adapters.

## Main Screens

- Pantry List: shows saved pantry ingredients from SQLite.
- Add/Edit Ingredient: captures name, quantity, unit, and optional expiry date.
- Suggested Recipes: lists only recipes that pass the strict matching rule.
- Recipe Detail: shows the full ingredient list and method.
- Settings: saves basic profile preferences with `SharedPreferences`.

## Strict Matching Rule

The core business rule is implemented in `MatchUtils.canMakeRecipe`. A recipe is suggested only when every required ingredient exists in the pantry and the available quantity is enough. Simple plural ingredient names and basic unit conversions such as kg/g and l/ml are handled so the match is not limited to exact text.

## Running the App

1. Open this folder in Android Studio.
2. Let Gradle sync the project.
3. Select an emulator or physical Android device.
4. Click Run.

The Gradle wrapper pins Gradle 9.1.0. This build was verified with Android SDK
36, Android Gradle Plugin 9.0.1 and JDK 25. Set `JAVA_HOME` to your JDK and run
`gradlew.bat assembleDebug` on Windows (`./gradlew assembleDebug` elsewhere).
Run `test-local.ps1` on Windows to compile and execute 22 pure-Java regression
checks. No cloud account, map service, location permission or payment API is
required.

## Final Verification Changes

- Aggregate duplicate pantry rows and duplicate recipe requirements.
- Compare compatible mass/volume units, including tablespoon/teaspoon volume.
- Reject non-finite quantities and impossible calendar dates.
- Refresh recipe results when returning from an edit.
- Use the toolbar menu to navigate between Pantry, Recipes and Settings.
- Apply expiry-warning and display-unit preferences to pantry rows.
- Preserve existing pantry data when reporting a future schema upgrade as
  unsupported, rather than dropping all tables.

The settings only affect display. They do not change stored ingredient amounts
or remove expired items from matching. Expiry warnings are informational, not a
food-safety assessment. Kitchen display converts exact spoon quantities only;
it does not assume that a mass can be converted into a volume.

## Submission Status

The source and existing local history were published on 22 October 2026 to
https://github.com/STARBOYINNIT/SmartPantryManager . This publication preserves
the original commit dates; it does not establish GitHub hosting from the start.
The narrated live demonstration, signed declaration and student review remain
outstanding. The earlier silent video is a rehearsal
aid and predates the final verification fixes. Do not submit it unchanged.
Review and acknowledge AI development and documentation assistance as required
by the institution. The local commit count must not be represented as proof
that development was hosted on GitHub from the start.

## Suggested Demo Flow

1. Show the pantry list and existing empty state.
2. Add ingredients for a recipe.
3. Open Suggested Recipes and show that the recipe appears only when all required ingredients are present.
4. Delete or reduce one ingredient and show that the recipe disappears.
5. Edit and delete a pantry item.
6. Close and reopen the app to show that SQLite persistence works.

## Files to Explain in the Video

- `DatabaseHelper.java`: creates the SQLite tables, seeds recipes, and performs CRUD.
- `MatchUtils.java`: applies the strict recipe matching logic.
- `MainActivity.java`: loads pantry items and navigates to other screens.
- `IngredientFormActivity.java`: validates and saves pantry form data.
- `PantryAdapter.java` and `RecipeAdapter.java`: display dynamic list rows.

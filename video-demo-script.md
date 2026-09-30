# Smart Pantry Manager Video Demo Script

Target length: 5 to 7 minutes.

## 1. GitHub Walkthrough - About 1 Minute

Open the GitHub repository and show the commit history.

Suggested narration:

"This is my Smart Pantry Manager repository. I used Git during development and made incremental commits for the project setup, database, CRUD screens, recipe matching logic, validation, UI improvements, and documentation. The commit history shows the project being developed step by step rather than uploaded only at the end."

## 2. Live App Demonstration - About 2 to 3 Minutes

Show the running app on the emulator.

Demo order:

1. Open the Pantry List screen and show the empty state.
2. Add tomato as a pantry item.
3. Open Suggested Recipes and show that no recipe matches yet.
4. Add eggs as a pantry item.
5. Open Suggested Recipes again and show Tomato Egg Scramble appears.
6. Open the recipe detail screen and show ingredients and method.
7. Edit a pantry item quantity.
8. Delete a pantry item and show the list updates.
9. Close and reopen the app to show that pantry data persists.

## 3. Code Explanation - About 2 to 3 Minutes

Explain three or more of these:

- `DatabaseHelper.java`: SQLite tables, seeded recipes, CRUD methods.
- `MatchUtils.java`: strict matching rule.
- `MainActivity.java`: loading pantry data and using Intents.
- `IngredientFormActivity.java`: input validation and save/delete behavior.
- `PantryAdapter.java` and `RecipeAdapter.java`: displaying database data in list rows.

Important point:

"A recipe is only suggested if every required ingredient is in the pantry in enough quantity. If one ingredient is missing, the recipe is excluded."

## 4. Database Justification - About 30 Seconds

Suggested narration:

"I chose SQLite because this app works well as an offline local pantry tracker. The user data is stored on the device, it persists after the app closes, and SQLite is simple to demonstrate and explain for this module."

## 5. Closing - About 15 Seconds

Suggested narration:

"Smart Pantry Manager meets the assignment requirements by using Java, multiple Android screens, Intents, SQLite persistence, CRUD, list adapters, seeded recipes, and strict recipe matching to help reduce food waste."

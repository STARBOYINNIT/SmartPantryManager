# Smart Pantry Manager

Smart Pantry Manager is a Java Android application for Mobile App Development 700. It helps users reduce food waste by tracking ingredients they already have and suggesting only recipes that can be cooked with the current pantry.

## Database Choice

This project uses SQLite through `SQLiteOpenHelper`. SQLite was chosen because it runs locally on the device, works offline, is covered in many Android persistence lessons, and is simple to demonstrate during the video.

## Core Features

- Add, view, edit, and delete pantry ingredients.
- Store pantry data persistently in SQLite.
- Seed 20 recipes on first run.
- Suggest recipes only when every required ingredient exists in the pantry in the required quantity.
- Show recipe details with ingredients and preparation steps.
- Include a settings screen for expiring-soon alerts and unit preference.
- Use Java Activities, Intents, ListView, and custom adapters.

## Running the App

1. Open this folder in Android Studio.
2. Let Gradle sync the project.
3. Select an emulator or physical Android device.
4. Click Run.

## Suggested Demo Flow

1. Show the pantry list and existing empty state.
2. Add ingredients for a recipe.
3. Open Suggested Recipes and show that the recipe appears only when all required ingredients are present.
4. Delete or reduce one ingredient and show that the recipe disappears.
5. Edit and delete a pantry item.
6. Close and reopen the app to show that SQLite persistence works.

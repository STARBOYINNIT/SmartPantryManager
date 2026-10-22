package com.example.smartpantry;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public abstract class NavigationActivity extends Activity {
    @Override protected void onPostCreate(android.os.Bundle state) {
        super.onPostCreate(state);
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this::applySystemInsets);
    }

    @Override public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) applySystemInsets();
    }

    private void applySystemInsets() {
        if (android.os.Build.VERSION.SDK_INT >= 35) {
            android.view.View content = findViewById(android.R.id.content);
            android.view.WindowInsets insets = getWindow().getDecorView().getRootWindowInsets();
            if (insets != null) {
                int barHeight = getActionBar() == null ? 0 : getActionBar().getHeight();
                int top = insets.getSystemWindowInsetTop() + barHeight;
                if (content.getPaddingTop() != top || content.getPaddingBottom() != insets.getSystemWindowInsetBottom()) {
                    content.setPadding(insets.getSystemWindowInsetLeft(), top,
                            insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
                }
            }
            if (getWindow().getInsetsController() != null) {
                int lightBars = android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                        | android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS;
                getWindow().getInsetsController().setSystemBarsAppearance(lightBars, lightBars);
            }
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Pantry");
        menu.add(0, 2, 1, "Recipes");
        menu.add(0, 3, 2, "Settings");
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        Class<?> destination;
        switch (item.getItemId()) {
            case 1: destination = MainActivity.class; break;
            case 2: destination = SuggestedRecipesActivity.class; break;
            case 3: destination = SettingsActivity.class; break;
            default: return super.onOptionsItemSelected(item);
        }
        if (getClass() != destination) {
            startActivity(new Intent(this, destination).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
        }
        return true;
    }
}

package com.example.smartpantry;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsActivity extends NavigationActivity {
    private static final String PREFS = "smart_pantry_settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        CheckBox expiryAlertCheck = findViewById(R.id.expiryAlertCheck);
        RadioGroup unitGroup = findViewById(R.id.unitGroup);
        Button saveSettingsButton = findViewById(R.id.saveSettingsButton);
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        expiryAlertCheck.setChecked(prefs.getBoolean("expiry_alerts", true));
        String units = prefs.getString("units", "metric");
        unitGroup.check(units.equals("simple") ? R.id.simpleOption : R.id.metricOption);

        saveSettingsButton.setOnClickListener(v -> {
            String selectedUnits = unitGroup.getCheckedRadioButtonId() == R.id.simpleOption ? "simple" : "metric";
            prefs.edit()
                    .putBoolean("expiry_alerts", expiryAlertCheck.isChecked())
                    .putString("units", selectedUnits)
                    .apply();
            Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}

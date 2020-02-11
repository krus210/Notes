package com.krus210.notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private static final String FROM_SETTINGS_KEY = "FROM_SETTINGS_KEY";

    public static void startFromSettings(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        intent.putExtra(FROM_SETTINGS_KEY, true);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
    }

    private void initViews() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        final EditText editPin = findViewById(R.id.edit_pin);
        Button buttonSavePin = findViewById(R.id.button_save_pin);

        buttonSavePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pinToSave = editPin.getText().toString();
                if (pinToSave.length() == 4) {
                    App.getKeystore().saveNew(pinToSave);
                    if (getIntent().hasExtra(FROM_SETTINGS_KEY) &&
                            getIntent().getBooleanExtra(FROM_SETTINGS_KEY, false)) {
                        finish();
                    } else {
                        Intent intent = new Intent(SettingsActivity.this,
                                ListNotesActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(SettingsActivity.this,
                            getString(R.string.write_four_digits),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
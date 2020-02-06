package com.krus210.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

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
                    Intent intent = new Intent(SettingsActivity.this,
                            ListNotesActivity.class);
                    startActivity(intent);
                    finish();
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
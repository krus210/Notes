package com.krus210.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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
        final ImageButton buttonShowPin = findViewById(R.id.button_show_pin);

        buttonShowPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonShowPin.getContentDescription() ==
                        getString(R.string.invisible_description)) {
                    editPin.setTransformationMethod(null);
                    buttonShowPin.setBackgroundResource(R.drawable.ic_visibility_black_24dp);
                    buttonShowPin.setContentDescription(getString(R.string.visible_description));
                } else {
                    editPin.setTransformationMethod(new PasswordTransformationMethod());
                    buttonShowPin.setBackgroundResource(R.drawable.ic_visibility_off_black_24dp);
                    buttonShowPin.setContentDescription(getString(R.string.invisible_description));

                }
            }
        });

        buttonSavePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pinToSave = editPin.getText().toString();
                App.getKeystore().saveNew(pinToSave);
                Intent intent = new Intent(SettingsActivity.this,
                        ListNotesActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

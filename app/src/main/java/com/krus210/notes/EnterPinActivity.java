package com.krus210.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class EnterPinActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String ENTER_PIN_SETTINGS = "enter_pin_settings";
    private static final String ENTER_PIN = "enter_pin";
    ImageButton buttonLight1;
    ImageButton buttonLight2;
    ImageButton buttonLight3;
    ImageButton buttonLight4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pin);
        initViews();
    }

    private void initViews() {
        if (!App.getKeystore().hasPin()) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            finish();
        }
        sharedPreferences = this.getSharedPreferences(ENTER_PIN_SETTINGS, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(ENTER_PIN)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ENTER_PIN, "");
            editor.apply();
        }
        buttonLight1 = findViewById(R.id.button_light_1);
        buttonLight2 = findViewById(R.id.button_light_2);
        buttonLight3 = findViewById(R.id.button_light_3);
        buttonLight4 = findViewById(R.id.button_light_4);

    }

    public void onClickNumber(View view) {
        Button buttonView = (Button) view;
        String enteredPin = "";
        if (sharedPreferences.contains(ENTER_PIN)) {
            enteredPin = sharedPreferences.getString(ENTER_PIN, "");
        }
        if (buttonView.getId() == R.id.button_back) {
            if (enteredPin.length() > 0) {
                enteredPin = enteredPin.substring(0, enteredPin.length()-1);
            }
        } else {
            enteredPin += buttonView.getText().toString();
        }
        switch (enteredPin.length()) {
            case 1:
                buttonLight1.setBackgroundResource(R.drawable.shape_oval_pink);
                buttonLight2.setBackgroundResource(R.drawable.shape_oval_gray);
                buttonLight3.setBackgroundResource(R.drawable.shape_oval_gray);
                buttonLight4.setBackgroundResource(R.drawable.shape_oval_gray);
                break;
            case 2:
                buttonLight1.setBackgroundResource(R.drawable.shape_oval_pink);
                buttonLight2.setBackgroundResource(R.drawable.shape_oval_pink);
                buttonLight3.setBackgroundResource(R.drawable.shape_oval_gray);
                buttonLight4.setBackgroundResource(R.drawable.shape_oval_gray);
                break;
            case 3:
                buttonLight1.setBackgroundResource(R.drawable.shape_oval_pink);
                buttonLight2.setBackgroundResource(R.drawable.shape_oval_pink);
                buttonLight3.setBackgroundResource(R.drawable.shape_oval_pink);
                buttonLight4.setBackgroundResource(R.drawable.shape_oval_gray);
                break;
            case 4:
                buttonLight1.setBackgroundResource(R.drawable.shape_oval_pink);
                buttonLight2.setBackgroundResource(R.drawable.shape_oval_pink);
                buttonLight3.setBackgroundResource(R.drawable.shape_oval_pink);
                buttonLight4.setBackgroundResource(R.drawable.shape_oval_pink);
                break;
            default:
                buttonLight1.setBackgroundResource(R.drawable.shape_oval_gray);
                buttonLight2.setBackgroundResource(R.drawable.shape_oval_gray);
                buttonLight3.setBackgroundResource(R.drawable.shape_oval_gray);
                buttonLight4.setBackgroundResource(R.drawable.shape_oval_gray);
        }
        if (enteredPin.length() == 4) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ENTER_PIN, "");
            editor.apply();
            if (App.getKeystore().checkPin(enteredPin)) {
                Intent intent = new Intent(this, ListNotesActivity.class);
                startActivity(intent);
                finish();
            } else {
                buttonLight1.setBackgroundResource(R.drawable.shape_oval_gray);
                buttonLight2.setBackgroundResource(R.drawable.shape_oval_gray);
                buttonLight3.setBackgroundResource(R.drawable.shape_oval_gray);
                buttonLight4.setBackgroundResource(R.drawable.shape_oval_gray);
                Toast.makeText(this, getString(R.string.not_equal_pin),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ENTER_PIN, enteredPin);
            editor.apply();
        }
    }

}

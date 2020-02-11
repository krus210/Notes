package com.krus210.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class EnterPinActivity extends AppCompatActivity {

    private static final String PIN_FROM_INSTANCE_STATE = "pin_from_instance_state";
    private ImageButton buttonLight1;
    private ImageButton buttonLight2;
    private ImageButton buttonLight3;
    private ImageButton buttonLight4;
    private String enteredPin;

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
        enteredPin = "";
        buttonLight1 = findViewById(R.id.button_light_1);
        buttonLight2 = findViewById(R.id.button_light_2);
        buttonLight3 = findViewById(R.id.button_light_3);
        buttonLight4 = findViewById(R.id.button_light_4);

    }

    public void onClickNumber(View view) {
        Button buttonView = (Button) view;

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
            if (App.getKeystore().checkPin(enteredPin)) {
                Intent intent = new Intent(this, ListNotesActivity.class);
                startActivity(intent);
                finish();
            } else {
                enteredPin = "";
                buttonLight1.setBackgroundResource(R.drawable.shape_oval_gray);
                buttonLight2.setBackgroundResource(R.drawable.shape_oval_gray);
                buttonLight3.setBackgroundResource(R.drawable.shape_oval_gray);
                buttonLight4.setBackgroundResource(R.drawable.shape_oval_gray);
                Toast.makeText(this, getString(R.string.not_equal_pin),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(PIN_FROM_INSTANCE_STATE, enteredPin);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(PIN_FROM_INSTANCE_STATE)) {
            enteredPin = savedInstanceState.getString(PIN_FROM_INSTANCE_STATE, "");
        }
    }
}

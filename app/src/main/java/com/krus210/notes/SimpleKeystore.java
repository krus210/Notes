package com.krus210.notes;

import android.content.Context;
import android.content.SharedPreferences;

public class SimpleKeystore implements Keystore {

    private SharedPreferences sharedPreferences;
    private static final String APP_SETTINGS = "settings";
    private static final String PIN = "pin";

    SimpleKeystore(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);

    }

    @Override
    public boolean hasPin() {
        return sharedPreferences.contains(PIN);
    }

    @Override
    public boolean checkPin(String pin) {
        String savedPin = sharedPreferences.getString(PIN, "");
        return pin.equals(savedPin);
    }

    @Override
    public void saveNew(String pin) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PIN, pin);
        editor.apply();
    }
}

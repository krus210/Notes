package com.krus210.notes;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editSnippet;
    CheckBox checkBoxDeadline;
    EditText editDateDeadline;
    Button buttonShowCalendar;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        initViews();
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar_create_note);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        editTitle = findViewById(R.id.edit_title);
        editSnippet = findViewById(R.id.edit_snippet);
        checkBoxDeadline = findViewById(R.id.checkbox_deadline);
        editDateDeadline = findViewById(R.id.edit_date_deadline);
        buttonShowCalendar = findViewById(R.id.button_show_calendar);

        editDateDeadline.setEnabled(false);
        buttonShowCalendar.setEnabled(false);

        checkBoxDeadline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!buttonView.isChecked()) {
                    editDateDeadline.setEnabled(false);
                    buttonShowCalendar.setEnabled(false);
                } else {
                    editDateDeadline.setEnabled(true);
                    buttonShowCalendar.setEnabled(true);
                    String currentDateAndTime = getCurrentDateTime(R.string.date_time_format);
                    editDateDeadline.setText(currentDateAndTime);
                }
            }
        });
    }

    public void onClickShowCalendar(View view) {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String dateFormat;
                        if (month < 10) {
                            dateFormat = getString(R.string.date_format_month_lower_10);
                        } else {
                            dateFormat = getString(R.string.date_format_month_upper_9);
                        }
                        String currentTime = getCurrentDateTime(R.string.time_format);
                        String dateFromCalendar = String.format(
                                dateFormat, day, month + 1, year, currentTime);
                        editDateDeadline.setText(dateFromCalendar);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save_note) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getCurrentDateTime(int formatDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(getString(formatDate),
                Locale.getDefault());
        return sdf.format(new Date());
    }

    @Override
    protected void onPause() {
        super.onPause();
        String title = editTitle.getText().toString();
        String snippet = editSnippet.getText().toString();
        Note note;
        if (checkBoxDeadline.isChecked()) {
            SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.date_time_format),
                    Locale.getDefault());
            Date dateDeadline;
            try {
                dateDeadline = sdf.parse(editDateDeadline.getText().toString());
                note = new Note(title, snippet, dateDeadline);
            } catch (ParseException e) {
                Toast.makeText(this,
                        getString(R.string.error_date_format), Toast.LENGTH_LONG).show();
                note = new Note(title, snippet);
            }
        } else {
            note = new Note(title, snippet);
        }
        App.getNoteRepository().saveNote(note);

    }
}

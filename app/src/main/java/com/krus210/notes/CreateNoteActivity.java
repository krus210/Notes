package com.krus210.notes;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editSnippet;
    private CheckBox checkBoxDeadline;
    private EditText editDateDeadline;
    private ImageButton buttonShowCalendar;
    private static final String ID_FROM_LIST_NOTES_ACTIVITY = "id_list_notes_activity";
    private static final String ID_FROM_SAVED_INSTANT_STATE = "id_from_saved_instant_state";
    private static final String TITLE_FROM_SAVED_INSTANT_STATE = "title_from_saved_instant_state";
    private static final String SNIPPET_FROM_SAVED_INSTANT_STATE =
            "snippet_from_saved_instant_state";
    private static final String CHECKBOX_FROM_SAVED_INSTANT_STATE =
            "checkbox_from_saved_instant_state";
    private static final String DEADLINE_FROM_SAVED_INSTANT_STATE =
            "deadline_from_saved_instant_state";
    private String id;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat simpleTimeFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        initViews();
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar_create_note);
        setSupportActionBar(toolbar);
        simpleDateFormat = new SimpleDateFormat(
                getString(R.string.date_time_format),
                Locale.getDefault());
        simpleTimeFormat = new SimpleDateFormat(
                getString(R.string.time_format),
                Locale.getDefault());

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

        id = getIntent().getStringExtra(ID_FROM_LIST_NOTES_ACTIVITY);
        if (id != null) {
            Note note = App.getNoteRepository().getNoteById(id);
            editTitle.setText(note.getTitle());
            editSnippet.setText(note.getSnippet());
            if (note.getDateDeadline() != null) {
                String dateDeadline = simpleDateFormat.format(note.getDateDeadline());
                checkBoxDeadline.setChecked(true);
                editDateDeadline.setEnabled(true);
                buttonShowCalendar.setEnabled(true);
                editDateDeadline.setText(dateDeadline);
            }
        }


        checkBoxDeadline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!buttonView.isChecked()) {
                    editDateDeadline.setEnabled(false);
                    buttonShowCalendar.setEnabled(false);
                } else {
                    editDateDeadline.setEnabled(true);
                    buttonShowCalendar.setEnabled(true);
                    String currentDateAndTime = simpleDateFormat.format(new Date());
                    editDateDeadline.setText(currentDateAndTime);
                }
            }
        });
    }

    public void onClickShowCalendar(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String dateFormat;
                        if (month < 10) {
                            dateFormat = getString(R.string.date_format_month_lower_10);
                        } else {
                            dateFormat = getString(R.string.date_format_month_upper_9);
                        }
                        String currentTime = simpleTimeFormat.format(new Date());
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        String title = editTitle.getText().toString();
        String snippet = editSnippet.getText().toString();
        Note note;
        if (checkBoxDeadline.isChecked()) {
            Date dateDeadline;
            try {
                dateDeadline = simpleDateFormat.parse(editDateDeadline.getText().toString());
                note = new Note(title, snippet, dateDeadline);
            } catch (ParseException e) {
                Toast.makeText(this,
                        getString(R.string.error_date_format), Toast.LENGTH_LONG).show();
                note = new Note(title, snippet);
            }
        } else {
            note = new Note(title, snippet);
        }
        if (!(title.equals("") && snippet.equals("") && note.getDateDeadline() == null)) {
            if (id != null) {
                note.setId(id);
            }
            App.getNoteRepository().saveNote(note);
            if (id == null) {
                id = note.getId();
            }
            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
        }
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(ID_FROM_SAVED_INSTANT_STATE, id);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(ID_FROM_SAVED_INSTANT_STATE)) {
            id = savedInstanceState.getString(ID_FROM_SAVED_INSTANT_STATE);
        }
    }
}

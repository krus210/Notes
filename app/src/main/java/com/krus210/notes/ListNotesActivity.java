package com.krus210.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ListNotesActivity extends AppCompatActivity {

    private final int REQUESTED_CODE_CREATE_NOTE_ACTIVITY = 1;
    private final String ID_FROM_LIST_NOTES_ACTIVITY = "id_list_notes_activity";
    private List<Note> notes;
    ItemsNoteAdapter adapter;
    Intent intentCreateNote;
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        initViews();

    }

    private void initViews() {
        FloatingActionButton fabCreateNote = findViewById(R.id.fab_create_note);
        intentCreateNote = new Intent(ListNotesActivity.this,
                CreateNoteActivity.class);
        fabCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentCreateNote.removeExtra(ID_FROM_LIST_NOTES_ACTIVITY);
                startActivityForResult(intentCreateNote, REQUESTED_CODE_CREATE_NOTE_ACTIVITY);
            }
        });
        ListView listView = findViewById(R.id.list_view);
        notes = App.getNoteRepository().getNotes();
        adapter = new ItemsNoteAdapter(notes);
        listView.setAdapter(adapter);
        alertDialogBuilder = new AlertDialog.Builder(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Note selectedNote = notes.get(position);
                intentCreateNote.putExtra(ID_FROM_LIST_NOTES_ACTIVITY, selectedNote.getId());
                startActivityForResult(intentCreateNote, REQUESTED_CODE_CREATE_NOTE_ACTIVITY);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,
                                           View view, final int position, long id) {
                alertDialogBuilder.setIcon(R.drawable.ic_launcher_background)
                        .setTitle(R.string.attention)
                        .setMessage(R.string.message_delete_note)
                        .setPositiveButton(R.string.button_delete,
                                new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Note note = App.getNoteRepository().getNotes().get(position);
                                App.getNoteRepository().deleteById(note.getId());
                                adapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                alertDialogBuilder.show();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUESTED_CODE_CREATE_NOTE_ACTIVITY:
                if(resultCode == RESULT_OK){
                    adapter.notifyDataSetChanged();
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}

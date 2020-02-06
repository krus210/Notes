package com.krus210.notes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FileNoteRepository implements NoteRepository {

    private Context context;
    private GsonBuilder gsonBuilder;
    private Gson gson;

    FileNoteRepository(Context context) {
        this.context = context;
    }

    @Override
    public Note getNoteById(String id) {
        String jsonText = readFromFile(context, id);
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        return gson.fromJson(jsonText, Note.class);
    }

    @Override
    public List<Note> getNotes() {
        List<Note> notes = new ArrayList<>();
        String[] savedFilesArray = context.fileList();
        for (String id : savedFilesArray) {
            Note note = getNoteById(id);
            notes.add(note);
        }
        Collections.sort(notes, new NoteComparator());
        return notes;
    }

    @Override
    public void saveNote(Note note) {
        String id;
        if (note.getId() != null) {
            id = note.getId();
        } else {
            id = UUID.randomUUID().toString();
            note.setId(id);
        }
        note.setDateLastChange(new Date());
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        String message = gson.toJson(note);
        saveToFile(context, message, id);
        Log.d("log", message);
    }

    @Override
    public void deleteById(String id) {
        context.deleteFile(id);
    }

    private void saveToFile(Context context, String message, String fileName) {
         try (FileOutputStream fileOutputStream = context
                 .openFileOutput(fileName, Context.MODE_PRIVATE);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
             BufferedWriter bw = new BufferedWriter(outputStreamWriter)) {
             bw.write(message);
        } catch (IOException e) {
            Toast.makeText(context,
                    context.getString(R.string.error_save_file),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private  String readFromFile(Context context, String fileName) {
        StringBuilder content = new StringBuilder();
        try (FileInputStream fileInputStream = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader br = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(context,
                    context.getString(R.string.error_file_not_exist),
                    Toast.LENGTH_SHORT).show();
        } catch (IOException i) {
            Toast.makeText(context,
                    context.getString(R.string.error_read_file),
                    Toast.LENGTH_SHORT).show();
        }
        return String.valueOf(content).trim();
    }


}

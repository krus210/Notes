package com.krus210.notes;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class FileNoteRepository implements NoteRepository {

    private Context context;

    public FileNoteRepository(Context context) {
        this.context = context;
    }

    @Override
    public Note getNoteById(String id) {
        String[] content = readFromFile(context, id)
                .split(context.getString(R.string.file_tag));
        String title = content[0];
        String snippet = content[1];
        String nullTag = context.getString(R.string.null_tag);
        if (title.equals(nullTag)) {
            title = "";
        }
        if (snippet.equals(nullTag)) {
            snippet = "";
        }
        Note note = new Note(title,snippet);
        if (content.length > 2) {
            String stringDeadLine = content[2];
            SimpleDateFormat sdf = new SimpleDateFormat(context.
                    getString(R.string.date_time_format),
                    Locale.getDefault());
            Date dateDeadline;
            try {
                dateDeadline = sdf.parse(stringDeadLine);
                note.setDateDeadline(dateDeadline);
            } catch (ParseException e) {
                Toast.makeText(context,
                        context.getString(R.string.error_date_format),
                        Toast.LENGTH_LONG).show();
            }
        }
        note.setId(id);
        File file = new File(context.getFilesDir() + "/" + id);
        Date dateLastChange= new Date(file.lastModified());
        note.setDateLastChange(dateLastChange);
        return note;
    }

    @Override
    public List<Note> getNotes() {
        List<Note> notes = new ArrayList<>();
        String[] savedFilesArray = context.fileList();
        for (String id : savedFilesArray) {
            Note note = getNoteById(id);
            notes.add(note);
        }
        notes.sort(new NoteComparator());
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
        String fileTag = context.getString(R.string.file_tag);
        String nullTag = context.getString(R.string.null_tag);
        String title = note.getTitle();
        String snippet = note.getSnippet();
        if (title.equals("")) {
            title = nullTag;
        }
        if (snippet.equals("")) {
            snippet = nullTag;
        }
        String message = String.format("%s%s%s", title,fileTag, snippet);
        if (note.getDateDeadline() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    context.getString(R.string.date_time_format),
                    Locale.getDefault());
            String dateDeadline = sdf.format(note.getDateDeadline());
            message = String.format("%s%s%s", message, fileTag, dateDeadline);
        }
        saveToFile(context, message, id);
    }

    @Override
    public void deleteById(String id) {
        context.deleteFile(id);
    }

    private void saveToFile(Context context, String message, String fileName) {
         try {
             FileOutputStream fileOutputStream = context.openFileOutput(fileName,
                     Context.MODE_PRIVATE);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
             BufferedWriter bw = new BufferedWriter(outputStreamWriter);
             bw.write(message);
             bw.close();

        } catch (IOException e) {
            Toast.makeText(context,
                    context.getString(R.string.error_save_file),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private  String readFromFile(Context context, String fileName) {
        StringBuilder content = new StringBuilder();
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(context,
                    context.getString(R.string.error_file_not_exist),
                    Toast.LENGTH_SHORT).show();
        } catch (IOException i) {
            Toast.makeText(context,
                    context.getString(R.string.error_read_file),
                    Toast.LENGTH_SHORT).show();
        }
        return String.valueOf(content);
    }


}

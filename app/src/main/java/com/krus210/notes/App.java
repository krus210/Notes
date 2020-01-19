package com.krus210.notes;

import android.app.Application;

public class App extends Application {
    private static NoteRepository noteRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        noteRepository = new ArrayNoteRepository();
    }

    public static NoteRepository getNoteRepository() {
        return noteRepository;
    }
}

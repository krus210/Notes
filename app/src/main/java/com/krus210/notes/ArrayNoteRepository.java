package com.krus210.notes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ArrayNoteRepository implements NoteRepository {

    private List<Note> notes;

    ArrayNoteRepository() {
        this.notes = new ArrayList<>();
    }

    @Override
    public Note getNoteById(String id) {
        Note voidNote = new Note(id);
        int indexNote = notes.indexOf(voidNote);
        return notes.get(indexNote);
    }

    @Override
    public List<Note> getNotes() {
        return this.notes;
    }

    @Override
    public void saveNote(Note note) {
        note.setDateLastChange(new Date());
        if (notes.contains(note)) {
            notes.remove(note);
            notes.add(note);
        } else {
            String id = UUID.randomUUID().toString();
            note.setId(id);
            this.notes.add(note);
        }
        notes.sort(new NoteComparator());
    }

    @Override
    public void deleteById(String id) {
        Note voidNote = new Note(id);
        notes.remove(voidNote);
    }
}

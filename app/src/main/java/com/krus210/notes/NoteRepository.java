package com.krus210.notes;

import java.util.List;

public interface NoteRepository {
    Note getNoteById(String id);
    List<Note> getNotes();
    void saveNote(Note note);
    void deleteById(String id);
}

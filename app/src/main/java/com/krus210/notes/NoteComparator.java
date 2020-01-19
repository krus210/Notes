package com.krus210.notes;

import java.util.Comparator;

public class NoteComparator implements Comparator<Note> {

    @Override
    public int compare(Note note, Note t1) {
        int comparisonDeadline = 0;
        if (note.getDateDeadline() != null) {
            comparisonDeadline = note.getDateDeadline().compareTo(t1.getDateDeadline());
        }
        if (comparisonDeadline == 0) {
            return note.getDateLastChange().compareTo(t1.getDateLastChange());
        }
        return comparisonDeadline;
    }
}

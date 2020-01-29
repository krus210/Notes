package com.krus210.notes;

import java.util.Comparator;

public class NoteComparator implements Comparator<Note> {

    @Override
    public int compare(Note note1, Note note2) {
        if (note1.getDateDeadline() != null && note2.getDateDeadline() != null) {
            int comparisonDeadline = note1.getDateDeadline().compareTo(note2.getDateDeadline());
            if (comparisonDeadline == 0) {
                return -note1.getDateLastChange().compareTo(note2.getDateLastChange());
            } else {
                return comparisonDeadline;
            }
        } else if (note1.getDateDeadline() != null && note2.getDateDeadline() == null) {
            return -1;
        } else if (note1.getDateDeadline() == null && note2.getDateDeadline() != null) {
            return 1;
        } else {
            return -note1.getDateLastChange().compareTo(note2.getDateLastChange());
        }

    }
}

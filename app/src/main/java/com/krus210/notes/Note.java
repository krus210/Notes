package com.krus210.notes;

import java.util.Date;
import java.util.Objects;

public class Note{

    private String id;
    private String title;
    private String snippet;
    private Date dateLastChange;
    private Date dateDeadline;

    public Note(String title, String snippet, Date dateDeadline) {
        this.title = title;
        this.snippet = snippet;
        this.dateDeadline = dateDeadline;
    }

    public Note(String title, String snippet) {
        this.title = title;
        this.snippet = snippet;
    }

    public Note(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public Date getDateLastChange() {
        return dateLastChange;
    }

    public void setDateLastChange(Date dateLastChange) {
        this.dateLastChange = dateLastChange;
    }

    public Date getDateDeadline() {
        return dateDeadline;
    }

    public void setDateDeadline(Date dateDeadline) {
        this.dateDeadline = dateDeadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return Objects.equals(getId(), note.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}

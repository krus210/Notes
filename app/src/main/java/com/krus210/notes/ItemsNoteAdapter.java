package com.krus210.notes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemsNoteAdapter extends BaseAdapter {

    private List<Note> items;

    ItemsNoteAdapter(List<Note> items) {
        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_list_layout, parent,false);
        }
        Note note = items.get(position);
        TextView textTitle = view.findViewById(R.id.text_card_view_title);
        textTitle.setText(note.getTitle());
        TextView textSnippet = view.findViewById(R.id.text_card_view_snippet);
        textSnippet.setText(note.getSnippet());
        TextView textDeadline = view.findViewById(R.id.text_card_view_deadline);
        if (note.getDateDeadline() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    parent.getContext().getString(R.string.date_time_format),
                    Locale.getDefault());
            String dateDeadline = sdf.format(note.getDateDeadline());
            textDeadline.setText(dateDeadline);
        } else {
            textDeadline.setText("");
            textDeadline.setVisibility(View.GONE);
        }
        return view;
    }
}

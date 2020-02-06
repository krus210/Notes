package com.krus210.notes;

import android.content.Context;
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
        if (note.getTitle().equals("")) {
            textTitle.setVisibility(View.GONE);
        } else {
            textTitle.setText(note.getTitle());
            textTitle.setVisibility(View.VISIBLE);
        }
        TextView textSnippet = view.findViewById(R.id.text_card_view_snippet);
        if (note.getSnippet().equals("")) {
            textSnippet.setVisibility(View.GONE);
        } else {
            textSnippet.setText(note.getSnippet());
            textSnippet.setVisibility(View.VISIBLE);
        }
        TextView textDeadline = view.findViewById(R.id.text_card_view_deadline);
        if (note.getDateDeadline() != null) {
            textDeadline.setVisibility(View.VISIBLE);
            SimpleDateFormat simpleDateFormat = getSimpleDateFormat(parent.getContext());
            String dateDeadline = simpleDateFormat.format(note.getDateDeadline());
            textDeadline.setText(dateDeadline);
        } else {
            textDeadline.setVisibility(View.GONE);
        }
        return view;
    }

    private SimpleDateFormat getSimpleDateFormat(Context context) {
        return new SimpleDateFormat(
                context.getString(R.string.date_time_format),
                Locale.getDefault());
    }
}

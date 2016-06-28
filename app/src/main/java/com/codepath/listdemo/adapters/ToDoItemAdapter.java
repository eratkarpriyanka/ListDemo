package com.codepath.listdemo.adapters;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.codepath.listdemo.R;

import java.util.List;

import com.codepath.listdemo.database.ToDoItemData;
import com.codepath.listdemo.model.ToDoItem;

public class ToDoItemAdapter extends CursorAdapter{


    private final ToDoItemData todoItemData;

    public ToDoItemAdapter(Context context, Cursor c, int flags ){
        super( context, c, flags );

        todoItemData = ToDoItemData.getInstance();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ToDoItem todoItem = todoItemData.getItemRecord(cursor);
        ViewHolder viewHolder = (ViewHolder)view.getTag();
        viewHolder.name.setText(todoItem.getName());
        String priority = todoItem.getPriority();
        viewHolder.priority.setText(priority);
        if(priority!=null && !priority.isEmpty()) {
            if (priority.equalsIgnoreCase("high")) {
                viewHolder.priority.setTextColor(Color.RED);
            } else if (priority.equalsIgnoreCase("medium")) {
                viewHolder.priority.setTextColor(Color.parseColor("#078f6b"));
            } else {
                viewHolder.priority.setTextColor(Color.LTGRAY);
            }
        }
        viewHolder.id = todoItem.getId();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater =  LayoutInflater.from(context);
        View rowView =
                inflater.inflate(R.layout.listview_item_todo, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.name = (TextView)rowView.findViewById(R.id.tvItemName);
        viewHolder.priority = (TextView) rowView.findViewById(R.id.tvItemPriority);
        rowView.setTag( viewHolder );
        return rowView;
    }

    // View lookup cache
    private static class ViewHolder {
        private long id;
        TextView name;
        TextView priority;
    }
}

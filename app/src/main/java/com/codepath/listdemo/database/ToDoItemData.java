package com.codepath.listdemo.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.codepath.listdemo.model.ToDoItem;

public class ToDoItemData {

    private static final String TABLE_NAME="items";
    private static final String KEY_ITEM_ID = "_id";
    private static final String KEY_ITEM="name";
    private static final String KEY_DATE="date";
    private static final String KEY_PRIORITY="priority";
    private static ToDoItemData todoItemData;

    private static ToDoItemDatabase db;

    public ToDoItemData(){
        db = ToDoItemDatabase.getInstance();
    }

    public static ToDoItemData getInstance(){
         if(todoItemData == null)
             todoItemData = new ToDoItemData();
        return todoItemData;
    }

    public void addItemData(ToDoItem toDoItem){
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ITEM, toDoItem.getName());
            values.put(KEY_DATE, toDoItem.getDate());
            values.put(KEY_PRIORITY, toDoItem.getPriority());
            long status = db.insertRecord(TABLE_NAME, null, values);
            Log.i("data", "insert" + status);
        }catch (Exception e){
            Log.e("data", "Exception" + e);
        }
    }

    public Cursor getItemList(){

        Cursor cursor=null;
        try{
            String strQuery = "SELECT *"+" FROM "+TABLE_NAME;
            cursor = db.rawQuery(strQuery, null);
            if ( cursor != null ) {

                Log.i("data", "CURSOR NOT NULL " + cursor.getCount());
            }

        }catch (Exception e){
            Log.e("data", "Exception" + e);
        }
        return cursor;
    }

    public ToDoItem getItemRecord(Cursor cursor){

        ToDoItem todoItem = new ToDoItem();
        try {

            if (cursor != null) {
                todoItem.setId(cursor.getInt(cursor.getColumnIndex(KEY_ITEM_ID)));
                todoItem.setName(cursor.getString(cursor.getColumnIndex(KEY_ITEM)));
                todoItem.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                todoItem.setPriority(cursor.getString(cursor.getColumnIndex(KEY_PRIORITY)));
                return todoItem;
            }
        }catch (Exception e) {

        }
        return null;
    }

    public ToDoItem getItemRecord(int itemId){

        Cursor cursor=null;
        String strQuery = "SELECT *"+" FROM "+TABLE_NAME;
        Log.i("data", "QUERY " + strQuery);
        try{
             cursor = db.rawQuery(strQuery, null);
             if ( cursor != null && cursor.getCount()>0) {

                 cursor.moveToPosition(itemId-1);

                 ToDoItem todoItem = new ToDoItem();
                 todoItem.setId(cursor.getInt(cursor.getColumnIndex(KEY_ITEM_ID)));
                 todoItem.setName(cursor.getString(cursor.getColumnIndex(KEY_ITEM)));
                 todoItem.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                 todoItem.setPriority(cursor.getString(cursor.getColumnIndex(KEY_PRIORITY)));
                 return todoItem;

        }
        }catch (Exception e){
            Log.e("data", "Exception" + e);
        }finally {
            cursor.close();
            cursor=null;
        }
    return null;
    }

    public void deleteItem(int itemId){

        String whereClause = KEY_ITEM_ID+" = "+itemId;
        try{
            Log.i("data", "clause" + whereClause);
            long status = db.deleteRecords(TABLE_NAME, whereClause, null);
            Log.i("data", "delete" + status);
        }catch (Exception e){
            Log.e("data", "Exception" + e);
        }
    }

    public void updateItem(int itemId,ToDoItem toDoItem){

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM, toDoItem.getName());
        values.put(KEY_DATE, toDoItem.getDate());
        values.put(KEY_PRIORITY, toDoItem.getPriority());
        String whereClause = KEY_ITEM_ID+" = "+itemId;
        Log.i("data", "where clause " +whereClause );
        try{
            long status = db.updateRecord(TABLE_NAME,null,values,whereClause);
            Log.i("data", "update" + status);
        }catch (Exception e){
            Log.e("data", "Exception" + e);
        }
    }
}

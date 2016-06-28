package com.codepath.listdemo.database;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.codepath.listdemo.MainApplication;

import java.io.File;

public class ToDoItemDatabase {

    // DB Info
    private static final String DATABASE_NAME = "todoitemdb";
    private static final int DATABASE_VERSION = 1;
    private String dbPath=MainApplication.appContext+"databases"+File.separator;

    //Table name
    private static final String TABLE_ITEMS = "items";

    //Table { @TABLE_ITEMS} Column
    private static final String KEY_ITEM_ID = "_id";
    private static final String KEY_ITEM_NAME = "name";
    private static final String KEY_ITEM_DATE = "date";
    private static final String KEY_ITEM_PRIORITY = "priority";


    private static final String CREATE_TABLE_ITEMS = "CREATE TABLE " + TABLE_ITEMS +
            "(" +
            KEY_ITEM_ID + " INTEGER PRIMARY KEY," + // Define a primary key
            KEY_ITEM_NAME + " TEXT," +
            KEY_ITEM_DATE + " TEXT," +
            KEY_ITEM_PRIORITY + " TEXT" +
            ")";
    private static ToDoItemDatabase todoItemDatabase;
    private final Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase dbObj;

    private ToDoItemDatabase(){

        context =MainApplication.appContext;
        init(context);
    }

    public static ToDoItemDatabase getInstance(){

        if(todoItemDatabase == null){
            todoItemDatabase = new ToDoItemDatabase();
        }
        return todoItemDatabase;
    }

    private void init(Context context) {
        databaseHelper = new DatabaseHelper(context);
        open();
    }

    public void open( )  {

        try {
            dbObj = databaseHelper.getWritableDatabase();
            Log.i("Database", "Version" + dbObj.getVersion());
        }catch (SQLException e){
            Log.e("Database", "Exception occured while opening db"+e);
        }
    }

    public void close( ) {
        dbObj.close( );
        databaseHelper.close();
    }

    public void beginTransaction( ) {
        dbObj.beginTransaction();
    }

    public void endTransSuccessfully( ) {
        try {
            dbObj.setTransactionSuccessful( );
        } catch ( Exception e ) {
            e.printStackTrace( );
        } finally {
            dbObj.endTransaction( );
        }
    }

    public SQLiteDatabase getConnection( ) {
        return dbObj;
    }

    public void deleteDB( String dbName ) {
        File checkDB = null;
        try {
            String myPath = dbName + dbName;
            checkDB = new File( myPath );
            if ( checkDB.exists( ) ) {
                checkDB.delete( );
                checkDB = null;
            }
        } catch ( Exception e ) {
            Log.e( "Database","db file not found");
        }

    }



    private static class DatabaseHelper extends SQLiteOpenHelper {


        public DatabaseHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TABLE_ITEMS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            if (oldVersion != newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
                onCreate(db);
            }
        }

    }

    public long insertRecord( String tableName, String nullColunmHack, ContentValues contentValues ) {
        long result = -1;
        SQLiteDatabase db = null;
        try {
            db = getConnection();
            result = db.insert(tableName, null, contentValues);
           } catch ( Exception e ) {
            Log.e("database","excp while inserting"+e);
            e.printStackTrace( );
        } finally {
            contentValues = null;
            db = null;
        }
        return result;
    }

    public long
    replaceRecord( String tableName, String nullColunmHack, ContentValues contentValues ) {
        long result = -1;
        SQLiteDatabase db = null;
        try {
            db = getConnection( );
            result = db.replace( tableName, null, contentValues );

        } catch ( Exception e ) {
            e.printStackTrace( );
        } finally {
            contentValues = null;
            db = null;
        }
        return result;
    }

    public long updateRecord( String tableName, String nullColunmHack, ContentValues contentValues,
                              String whereCond ) {
        long result = -1;
        SQLiteDatabase db = null;
        try {
            db = getConnection( );
            result = db.update( tableName, contentValues, whereCond, null );
        } catch ( Exception e ) {
            e.printStackTrace( );
        } finally {
            contentValues = null;
            db = null;
        }
        return result;
    }

    public long deleteRecords( String tableName, String whereClause, String[] whereArgs ) {
        long result = -1;
        SQLiteDatabase db = null;
        try {
            db = getConnection( );
            result = db.delete( tableName, whereClause, whereArgs );
        } catch ( Exception e ) {
            e.printStackTrace( );
        } finally {
            db = null;
        }
        return result;
    }

    public Cursor getRecords( boolean distinct, String table, String[] columns, String selection,
                              String[] selectionArgs, String groupBy, String having, String orderBy, String limit ) {
        Cursor mCursor = null;
        SQLiteDatabase db = null;
        try {
            db = getConnection( );
            mCursor =
                    db.query( distinct, table, columns, selection, selectionArgs, groupBy, having,
                            orderBy, limit );


        } catch ( Exception e ) {
            e.printStackTrace( );
        } finally {
            db = null;
        }
        return mCursor;
    }

    public long updateRecords( String tableName, ContentValues contentValues, String whereClause,
                               String[] whereArgs ) {
        long result = -1;
        SQLiteDatabase db = null;

        try {
            db = getConnection( );

            result = db.update(tableName, contentValues, whereClause, whereArgs);
        } catch ( Exception e ) {
            e.printStackTrace( );
        } finally {
            contentValues = null;
            db = null;
        }
        return result;
    }

    public Cursor rawQuery( String sql, String[] selectionArgs ) {

       // return dbObj.rawQuery( sql, selectionArgs );
        Cursor cursor=null;
        SQLiteDatabase db = null;
        try {
            db = getConnection();
            cursor= db.rawQuery(sql,selectionArgs);
        } catch ( Exception e ) {
            Log.e("database","excp while selecting"+e);
            e.printStackTrace( );
        } finally {

            db = null;
        }
        return cursor;
    }

}

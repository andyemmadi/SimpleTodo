package com.ramu.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramu on 2/12/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    //DATA BASE NAME;
    private static final String DATABASE_NAME = "TodoManager";
    //album table name
    private static final String TABLE_TODO = "todoTable";

    //album table columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "todo_title";
    private static final String KEY_DATE = "todo_date";
    private static final String KEY_TIME = "todo_time";
    private static final String KEY_PRIORITY = "todo_priority";
    private static final String KEY_NOTE = "todo_note";
    private static final String KEY_STATUS = "todo_status";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE "+ TABLE_TODO + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_TITLE + " TEXT," +
                KEY_DATE + " TEXT," +
                KEY_TIME + " TEXT," +
                KEY_PRIORITY + " TEXT," +
                KEY_NOTE + " TEXT," +
                KEY_STATUS + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXITS"+TABLE_TODO);
        Log.d("onUpgrade","databse deleted");
        onCreate(sqLiteDatabase);
    }


    void addTodoItem(TodoItemModel todoItem){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, todoItem.getTitle());
        values.put(KEY_DATE, todoItem.getDate());
        values.put(KEY_TIME, todoItem.getTime());
        values.put(KEY_PRIORITY, todoItem.getPriority());
        values.put(KEY_NOTE, todoItem.getNote());
        values.put(KEY_STATUS, todoItem.getStatus());

        //inserting a row
        db.insert(TABLE_TODO, null, values);
        db.close();
    }


    TodoItemModel getTodoItem(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TODO, new String[]{ KEY_ID, KEY_TITLE, KEY_DATE, KEY_TIME, KEY_PRIORITY, KEY_NOTE, KEY_STATUS},
                KEY_ID + " =?", new String[]{ String.valueOf(id)}, null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        TodoItemModel itemModel = new TodoItemModel(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4),cursor.getString(5),cursor.getString(6));
        return itemModel;

    }


    //getting list of all Todoitems
    public List<TodoItemModel> getAllTodoItems(){

        List<TodoItemModel> todoItemModelList = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+TABLE_TODO;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null);

        // looping through all rows and adding to list;

        if(cursor.moveToFirst()){
            do{

                TodoItemModel todoItem = new TodoItemModel();
                todoItem.setId(Integer.parseInt(cursor.getString(0)));
                todoItem.setTitle(cursor.getString(1));
                todoItem.setDate(cursor.getString(2));
                todoItem.setTime(cursor.getString(3));
                todoItem.setPriority(cursor.getString(4));
                todoItem.setNote(cursor.getString(5));
                todoItem.setStatus(cursor.getString(6));

                todoItemModelList.add(todoItem);
            }while (cursor.moveToNext());
        }        // returning all todoItems;
        return todoItemModelList;
    }

    public int updateTodoItem(TodoItemModel todoItemModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, todoItemModel.getTitle());
        values.put(KEY_DATE, todoItemModel.getDate());
        values.put(KEY_TIME, todoItemModel.getTime());
        values.put(KEY_PRIORITY, todoItemModel.getPriority());
        values.put(KEY_NOTE, todoItemModel.getNote());
        values.put(KEY_STATUS, todoItemModel.getStatus());

        //updating row



        return db.update(TABLE_TODO, values, KEY_ID + " =?",
                new String[]{String.valueOf(todoItemModel.getId())});

    }


    public void deleteTodoItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_ID + " =?", new String[]{String.valueOf(id)});
        Log.d("database delete","success");
        db.close();
    }


    public int getTodoListCount(){
        String countQuery = "SELECT * FROM "+TABLE_TODO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }


/*
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE "+ TABLE_ALBUM + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_SINGER + " TEXT," +
                KEY_ALBUM + " TEXT," +
                KEY_ICON + " TEXT," +
                KEY_RATE + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
        //setUpDataBase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXITS"+TABLE_ALBUM);
        Log.d("onUpgrade","databse deleted");
        onCreate(sqLiteDatabase);
    }




    // adding new contact

    void addAlbum(Album album){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SINGER, album.getSingerName());
        values.put(KEY_ALBUM, album.getSongName());
        values.put(KEY_ICON, album.getAlbumIcon());
        values.put(KEY_RATE, album.getRating());

        //inserting a row
        db.insert(TABLE_ALBUM, null, values);
        db.close();
    }


    // getting single contact

    Album getAlbum(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ALBUM, new String[]{ KEY_ID, KEY_SINGER, KEY_ALBUM, KEY_ICON, KEY_RATE},
                KEY_ID + " =?", new String[]{ String.valueOf(id)}, null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Album album = new Album(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));
        return  album;
    }

    //getting list of all contacts
    public List<Album> getAllAlbums(){

        List<Album> albumList = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+TABLE_ALBUM;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null);

        // looping through all rows and adding to list;

        if(cursor.moveToFirst()){
            do{

                Album album = new Album();
                album.set_id(Integer.parseInt(cursor.getString(0)));
                album.setSingerName(cursor.getString(1));
                album.setSongName(cursor.getString(2));
                album.setAlbumIcon(cursor.getString(3));
                album.setRating(cursor.getString(4));

                albumList.add(album);
            }while (cursor.moveToNext());
        }        // returning all contacts;
        return albumList;
    }


    public int updateAlbum(Album album){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SINGER, album.getSingerName());
        values.put(KEY_ALBUM, album.getSongName());
        values.put(KEY_ICON, album.getAlbumIcon());
        values.put(KEY_RATE, album.getRating());


        //updating row

        return db.update(TABLE_ALBUM, values, KEY_ID + " =?",
                new String[]{String.valueOf(album.get_id())});

    }


    public void deleteAlbum(Album album){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALBUM, KEY_ID + " =?", new String[]{String.valueOf(album.get_id())});

        Log.d("database delete","success");
        db.close();
    }


    public int getAlbumsCount(){
        String countQuery = "SELECT * FROM "+TABLE_ALBUM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public void deleteAllAblums(){
        String selectQuery = "DELETE * FROM "+TABLE_ALBUM;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.close();

    }*/
}

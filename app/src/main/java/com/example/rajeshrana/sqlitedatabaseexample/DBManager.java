package com.example.rajeshrana.sqlitedatabaseexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

/**
 * Created by rajesh.rana on 26-05-2017.
 */

public class DBManager {

    private SQLiteDatabase sqllitedb;
    static final String DB_NAME = "Students";
    static final String DB_TABLE = "Logins";
    static final String COL_USERNAME = "Username";
    static final String COL_PASSWORD = "Password";
    static final String COL_ID = "Id";
    static final int DB_VERSION = 1;


    // Create Table Query
    static final String CREATE_TABLE = "Create table IF NOT EXISTS "+DB_TABLE+"(ID integer PRIMARY KEY AUTOINCREMENT,"+COL_USERNAME+" text,"+COL_PASSWORD + " text);";


    public DBManager(Context context){
        DataBaseHelperUser dbHelper = new DataBaseHelperUser(context);
        sqllitedb = dbHelper.getWritableDatabase();
    }

    // Inner Helper Class
    static class DataBaseHelperUser extends SQLiteOpenHelper {
        Context context;

        public DataBaseHelperUser(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context, "Table is Created", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop table IF EXISTS "+DB_TABLE);
            onCreate(db);
        }
    }

    // Insert data
    public long Insert(ContentValues values){
        return sqllitedb.insert(DB_TABLE,"",values);
    }


    // Get Data
    public Cursor getData(){

        Cursor res = sqllitedb.rawQuery("select * from "+DB_TABLE ,null);
        return res;

    }


    // Update Data
    public boolean updateData(String id , String userName , String password){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID , id);
        contentValues.put(COL_USERNAME,userName);
        contentValues.put(COL_PASSWORD , password);
        sqllitedb.update(DB_TABLE , contentValues ,"ID = ?" ,new String[] {id} );
        return true;


    }

    public int deleteData(String user_id ){
        int count = sqllitedb.delete(DB_TABLE,"ID=?",new String[]{user_id});
        return count;
    }
}

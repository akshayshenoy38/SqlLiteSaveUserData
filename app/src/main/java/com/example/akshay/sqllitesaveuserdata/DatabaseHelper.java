package com.example.akshay.sqllitesaveuserdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Akshay on 13-02-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME  = "transport_table";

    // columns
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_MOBILE = "MOBILE";
    public static final String COLUMN_GENDER = "GENDER";
    public static final String COLUMN_FROM = "FRO";
    public static final String COLUMN_TO = "DEST";
    public static final String COLUMN_DATE = "DATE";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "onCreate: Databasehelper oncreate started");
        sqLiteDatabase.execSQL("create table "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "EMAIL TEXT," +
                "MOBILE TEXT," +
                "GENDER TEXT," +
                "FRO TEXT," +
                "DEST TEXT," +
                "DATE TEXT)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String name,String email,String mobile,String gender,String from,String to,String date){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME,name);
        contentValues.put(COLUMN_EMAIL,email);
        contentValues.put(COLUMN_MOBILE,mobile);
        contentValues.put(COLUMN_GENDER,gender);
        contentValues.put(COLUMN_FROM,from);
        contentValues.put(COLUMN_TO,to);
        contentValues.put(COLUMN_DATE,date);
        Log.d(TAG, "addData: "+name);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from "+TABLE_NAME,null);
        return res;
    }




    // now this is for updating from below
    public boolean updateFunction(String id,String name,String email,String mobile,String gender, String from, String to, String date){
        SQLiteDatabase db =this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID,id);
        contentValues.put(COLUMN_NAME,name);
        contentValues.put(COLUMN_EMAIL,email);
        contentValues.put(COLUMN_MOBILE,mobile);
        contentValues.put(COLUMN_GENDER,gender);
        contentValues.put(COLUMN_FROM,from);
        contentValues.put(COLUMN_TO,to);
        contentValues.put(COLUMN_DATE,date);
        db.update(TABLE_NAME,contentValues,"ID = ?",new String[]{id});
        return true;
    }
    public Integer deleteData(String id){
        SQLiteDatabase db =this.getWritableDatabase();

        return db.delete(TABLE_NAME,"ID = ?",new String[]{id});


    }
}

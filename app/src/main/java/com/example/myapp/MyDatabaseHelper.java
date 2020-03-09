package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "FirstAndroidApp.db";
    private static final int DATABASE_VERSION = 1;
    //Table1
    private static final String TABLE1_NAME = "user";
    private static final String TABLE1_UID = "uid";
    private static final String TABLE1_USERNAME = "username";
    private static final String TABLE1_PASSWORD = "password";
    private static final String TABLE1_PIN = "pin";
    private static final String CREATE_TABLE1 = "CREATE TABLE " + TABLE1_NAME +
            " (" + TABLE1_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE1_USERNAME + " TEXT UNIQUE, " +
            TABLE1_PASSWORD + " TEXT, " +
            TABLE1_PIN + " INTEGER);";
    //Table2
    private static final String TABLE2_NAME = "storage";
    private static final String TABLE2_ID = "id";
    private static final String TABLE2_USERNAME = "username";
    private static final String TABLE2_TITLE = "title";
    private static final String TABLE2_DESC = "description";
    private static final String TABLE2_ST_USERNAME = "st_username";
    private static final String TABLE2_ST_PASSWORD = "st_password";
    private static final String TABLE2_DATE = "date";
    private static final String CREATE_TABLE2 = "CREATE TABLE " + TABLE2_NAME +
            " (" + TABLE2_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE2_USERNAME + " TEXT REFERENCES TABLE1_NAME(TABLE1_USERNAME), " +
            TABLE2_TITLE + " TEXT, " +
            TABLE2_DESC + " TEXT, " +
            TABLE2_ST_USERNAME + " TEXT, " +
            TABLE2_ST_PASSWORD + " TEXT, " +
            TABLE2_DATE + " DATETIME);";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE2_NAME);
        onCreate(db);
    }

    public long addUser(String username,String password,String pin){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE1_USERNAME,username);
        contentValues.put(TABLE1_PASSWORD,password);
        contentValues.put(TABLE1_PIN,pin);
        long res = db.insert(TABLE1_NAME,null,contentValues);
        db.close();
        return res;
    }

    public boolean checkUser(String username,String password){
        String[] columns = {TABLE1_UID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = TABLE1_USERNAME + "= ?" + " AND " + TABLE1_PASSWORD + "= ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE1_NAME, columns, selection, selectionArgs, null, null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return true;
        else
            return false;
    }

    public boolean CheckUserPin(String username, String pin){
        String[] columns = {TABLE1_UID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = TABLE1_USERNAME + "= ?" + " AND " + TABLE1_PIN + "= ?";
        String[] selectionArgs = {username, pin};
        Cursor cursor = db.query(TABLE1_NAME, columns, selection, selectionArgs, null, null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return true;
        else
            return false;
    }

    public long updatePassword(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE1_PASSWORD,password);
        long res = db.update(TABLE1_NAME,contentValues, TABLE1_USERNAME + "= ?",new String[]{username});
        db.close();
        return res;
    }

    private String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, d MMM yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public long addToStorage(String username,String title,String stusername,String stpassword,String desc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE2_USERNAME,username);
        contentValues.put(TABLE2_TITLE,title);
        contentValues.put(TABLE2_ST_USERNAME,stusername);
        contentValues.put(TABLE2_ST_PASSWORD,stpassword);
        contentValues.put(TABLE2_DESC,desc);
        contentValues.put(TABLE2_DATE,getDateTime());
        long res = db.insert(TABLE2_NAME,null,contentValues);
        db.close();
        return res;
    }

    //Function get all storage
    public List<Storage> getStorage(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect={TABLE2_ID,TABLE2_USERNAME,TABLE2_TITLE,TABLE2_DESC,TABLE2_ST_USERNAME,TABLE2_ST_PASSWORD,TABLE2_DATE};

        qb.setTables(TABLE2_NAME);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);
        List<Storage> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Storage storage = new Storage();
                storage.setId(cursor.getInt(cursor.getColumnIndex(TABLE2_ID)));
                storage.setUsername(cursor.getString(cursor.getColumnIndex(TABLE2_USERNAME)));
                storage.setTitle(cursor.getString(cursor.getColumnIndex(TABLE2_TITLE)));
                storage.setDescription(cursor.getString(cursor.getColumnIndex(TABLE2_DESC)));
                storage.setSt_username(cursor.getString(cursor.getColumnIndex(TABLE2_ST_USERNAME)));
                storage.setSt_password(cursor.getString(cursor.getColumnIndex(TABLE2_ST_PASSWORD)));
                storage.setDate(cursor.getString(cursor.getColumnIndex(TABLE2_DATE)));

                result.add(storage);
            }while (cursor.moveToNext());
        }
        return result;
    }

    //Function get all storage username
    public List<String> getUsername(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect={TABLE2_USERNAME};

        qb.setTables(TABLE2_NAME);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);
        List<String> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                result.add(cursor.getString(cursor.getColumnIndex(TABLE2_USERNAME)));
            }while (cursor.moveToNext());
        }
        return result;
    }

    //Function get storage by username
    public List<Storage> getStorageByUsername(String username){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect={TABLE2_ID,TABLE2_USERNAME,TABLE2_TITLE,TABLE2_DESC,TABLE2_ST_USERNAME,TABLE2_ST_PASSWORD,TABLE2_DATE};

        qb.setTables(TABLE2_NAME);
        //SELECT * FROM storage WHERE username = ?
        Cursor cursor = qb.query(db,sqlSelect,"username = ?",new String[]{username},null,null,null);
        List<Storage> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Storage storage = new Storage();
                storage.setId(cursor.getInt(cursor.getColumnIndex(TABLE2_ID)));
                storage.setUsername(cursor.getString(cursor.getColumnIndex(TABLE2_USERNAME)));
                storage.setTitle(cursor.getString(cursor.getColumnIndex(TABLE2_TITLE)));
                storage.setDescription(cursor.getString(cursor.getColumnIndex(TABLE2_DESC)));
                storage.setSt_username(cursor.getString(cursor.getColumnIndex(TABLE2_ST_USERNAME)));
                storage.setSt_password(cursor.getString(cursor.getColumnIndex(TABLE2_ST_PASSWORD)));
                storage.setDate(cursor.getString(cursor.getColumnIndex(TABLE2_DATE)));

                result.add(storage);
            }while (cursor.moveToNext());
        }
        return result;
    }

    public boolean updateData(String id, String username,String title, String stusername, String stpassword, String desc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE2_TITLE,title);
        contentValues.put(TABLE2_USERNAME,username);
        contentValues.put(TABLE2_ST_USERNAME,stusername);
        contentValues.put(TABLE2_ST_PASSWORD,stpassword);
        contentValues.put(TABLE2_DESC,desc);
        contentValues.put(TABLE2_DATE,getDateTime());

        long result = db.update(TABLE2_NAME, contentValues, TABLE2_ID + " = ? ", new String[]{id});
        if(result == -1){
            Toast.makeText(context, "เกิดข้อผิดพลาดในการอัปเดตข้อมูล", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            Toast.makeText(context, "อัปเดตข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    void deleteOneRow(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE2_NAME, TABLE2_ID + " = ? ", new String[]{id});
        if(result == -1){
            Toast.makeText(context, "เกิดข้อผิดพลาดในการลบข้อมูล", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "ลบข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
        }
    }
}

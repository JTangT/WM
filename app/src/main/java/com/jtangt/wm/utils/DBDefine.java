package com.jtangt.wm.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.jtangt.wm.po.User;

public class DBDefine {
    private static final String DB_NAME = "user.db";
    private static final String DB_TABLE = "userinfo";
    private static final int DB_VERSION = 1;

    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_SEX="sex";
    public static final String KEY_TOKEN="token";
    public static final String KEY_STATS="stats";
    public static final String KEY_PICTURE="picture";

    private SQLiteDatabase db;
    private final Context context;
    private DBOpenHelper dbOpenHelper;

    public DBDefine(Context _context) {
        context = _context;
    }

    /** Close the database */
    public void close() {
        if (db != null){
            db.close();
            db = null;
        }
    }

    /** Open the database */
    public void open() throws SQLiteException {
        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbOpenHelper.getWritableDatabase();
        }
        catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }


    public long insert(User user) {
        ContentValues newValues = new ContentValues();

        newValues.put(KEY_ID, user.getId());
        newValues.put(KEY_USERNAME, user.getUsername());
        newValues.put(KEY_PASSWORD, user.getPassword());
        newValues.put(KEY_PHONE,user.getPhone());
        newValues.put(KEY_SEX,user.getSex());
        newValues.put(KEY_TOKEN,user.getToken());
        newValues.put(KEY_STATS,user.getStats());
        newValues.put(KEY_PICTURE,user.getPicturebase64());

        return db.insert(DB_TABLE, null, newValues);
    }


    public User[] queryAllData() {
        Cursor results =  db.query(DB_TABLE, new String[] { KEY_ID, KEY_USERNAME, KEY_PASSWORD, KEY_PHONE,KEY_SEX,KEY_TOKEN,KEY_STATS,KEY_PICTURE},
                null, null, null, null, null);
        return ConvertToStudent(results);
    }

    public void rmData(int id){
        db.execSQL("delete from "+DB_TABLE+" where id = "+id);
    }

//    public Student[] queryOneData(long id) {
//        Cursor results =  db.query(DB_TABLE, new String[] { KEY_ID, KEY_NAME, KEY_XUEHAO, KEY_FENGSHU},
//                KEY_ID + "=" + id, null, null, null, null);
//        return ConvertToStudent(results);
//    }

    private User[] ConvertToStudent(Cursor cursor){
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()){
            return null;
        }
        User[] users = new User[resultCounts];
        for (int i = 0 ; i<resultCounts; i++){
            users[i] = new User();
            users[i].setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            users[i].setUsername(cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
            users[i].setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
            users[i].setPhone(cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
            users[i].setSex(cursor.getInt(cursor.getColumnIndex(KEY_SEX)));
            users[i].setToken(cursor.getString(cursor.getColumnIndex(KEY_TOKEN)));
            users[i].setStats(cursor.getInt(cursor.getColumnIndex(KEY_STATS)));
            users[i].setPicturebase64(cursor.getString(cursor.getColumnIndex(KEY_PICTURE)));
            cursor.moveToNext();
        }
        return users;
    }

    /** 静态Helper类，用于建立、更新和打开数据库*/
    private static class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private static final String DB_CREATE = "create table " +
                DB_TABLE + " (" + KEY_ID + " integer primary key, " +
                KEY_USERNAME+ " text not null, " + KEY_PASSWORD+ " text not null," + KEY_PHONE + " text not null,"+KEY_SEX+" integer,"+KEY_TOKEN+" text not null,"+KEY_STATS+" integer,"+KEY_PICTURE+" text not null);";


        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(_db);
        }
    }
}

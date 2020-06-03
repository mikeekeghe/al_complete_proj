package util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rajesh on 2017-08-21.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static String DB_NAME = "attribute";
    private static int DB_VERSION = 1;
    private SQLiteDatabase db;

    public static final String ATTR_TABLE = "attribute_data";

    public static final String COLUMN_ATTR_GROUP_ID = "attr_group_id";
    public static final String COLUMN_ATTR_ID = "attr_id";
    public static final String COLUMN_ATTR_GROUP_CHOOSEN = "attr_group_choosen";

    private String MY_FAV_TABLE = "my_fav";
    private String COLUMN_POST_ID = "post_id";


    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        String exe = "CREATE TABLE IF NOT EXISTS " + ATTR_TABLE
                + "(" + COLUMN_ATTR_GROUP_ID + " integer, "
                + COLUMN_ATTR_ID + " TEXT NOT NULL,"
                + COLUMN_ATTR_GROUP_CHOOSEN + " TEXT NOT NULL"
                + ")";

        db.execSQL(exe);

        String exe_fav = "CREATE TABLE IF NOT EXISTS " + MY_FAV_TABLE
                + "(" + COLUMN_POST_ID + " integer primary key)";

        db.execSQL(exe_fav);

    }

    public boolean setAttrribute(HashMap<String, String> map) {
        db = getWritableDatabase();
        if (isInAttr(map.get(COLUMN_ATTR_GROUP_ID))) {

            db.execSQL("update " + ATTR_TABLE + " set " +
                    COLUMN_ATTR_ID + " = '" + map.get(COLUMN_ATTR_ID) + "'," +
                    COLUMN_ATTR_GROUP_CHOOSEN + " = '" + map.get(COLUMN_ATTR_GROUP_CHOOSEN) +
                    "' where " + COLUMN_ATTR_GROUP_ID + "=" + map.get(COLUMN_ATTR_GROUP_ID));
            return false;
        } else {
            ContentValues values = new ContentValues();

            values.put(COLUMN_ATTR_GROUP_ID, map.get(COLUMN_ATTR_GROUP_ID));
            values.put(COLUMN_ATTR_ID, map.get(COLUMN_ATTR_ID));
            values.put(COLUMN_ATTR_GROUP_CHOOSEN, map.get(COLUMN_ATTR_GROUP_CHOOSEN));

            db.insert(ATTR_TABLE, null, values);
            return true;
        }
    }

    public boolean setAttrributeMulti(HashMap<String, String> map) {
        db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ATTR_GROUP_ID, map.get(COLUMN_ATTR_GROUP_ID));
        values.put(COLUMN_ATTR_ID, map.get(COLUMN_ATTR_ID));
        values.put(COLUMN_ATTR_GROUP_CHOOSEN, map.get(COLUMN_ATTR_GROUP_CHOOSEN));

        db.insert(ATTR_TABLE, null, values);

        return true;
    }

    public boolean isInAttr(String id) {
        db = getReadableDatabase();
        String qry = "Select *  from " + ATTR_TABLE + " where " + COLUMN_ATTR_GROUP_ID + " = " + id;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) return true;

        return false;
    }

    public boolean isInAttr_id(String id) {
        db = getReadableDatabase();
        String qry = "Select *  from " + ATTR_TABLE + " where " + COLUMN_ATTR_ID + " = '" + id + "'";
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) return true;

        return false;
    }

    public ArrayList<HashMap<String, String>> getAllAttr() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        db = getReadableDatabase();
        String qry = "Select *  from " + ATTR_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put(COLUMN_ATTR_GROUP_ID, cursor.getString(cursor.getColumnIndex(COLUMN_ATTR_GROUP_ID)));
            map.put(COLUMN_ATTR_ID, cursor.getString(cursor.getColumnIndex(COLUMN_ATTR_ID)));
            map.put(COLUMN_ATTR_GROUP_CHOOSEN, cursor.getString(cursor.getColumnIndex(COLUMN_ATTR_GROUP_CHOOSEN)));

            list.add(map);
            cursor.moveToNext();
        }
        return list;
    }

    public void clearAttr() {
        db = getReadableDatabase();
        db.execSQL("delete from " + ATTR_TABLE);
    }

    public void removeItemFromAttr(String id) {
        db = getReadableDatabase();
        db.execSQL("delete from " + ATTR_TABLE + " where " + COLUMN_ATTR_ID + " = '" + id + "'");

        Log.e("DATA", "delete from " + ATTR_TABLE + " where " + COLUMN_ATTR_ID + " = '" + id + "'");
    }

    public boolean setFav(String post_id) {
        db = getWritableDatabase();
        if (isFav(post_id)) {
            db.execSQL("delete from " + MY_FAV_TABLE + " where " + COLUMN_POST_ID + "=" + post_id);
            return false;
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_POST_ID, post_id);
            db.insert(MY_FAV_TABLE, null, values);
            return true;
        }
    }

    public boolean isFav(String id) {
        db = getReadableDatabase();
        String qry = "Select *  from " + MY_FAV_TABLE + " where " + COLUMN_POST_ID + " = " + id;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) return true;

        return false;
    }

    public ArrayList<String> getFavAll() {
        ArrayList<String> list = new ArrayList<>();
        db = getReadableDatabase();
        String qry = "Select *  from " + MY_FAV_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            list.add(cursor.getString(cursor.getColumnIndex(COLUMN_POST_ID)));
            cursor.moveToNext();
        }
        return list;
    }

    public String getFavConcatString() {
        db = getReadableDatabase();
        String qry = "Select *  from " + MY_FAV_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        String concate = "";
        for (int i = 0; i < cursor.getCount(); i++) {
            if (concate.equalsIgnoreCase("")) {
                concate = cursor.getString(cursor.getColumnIndex(COLUMN_POST_ID));
            } else {
                concate = concate + "," + cursor.getString(cursor.getColumnIndex(COLUMN_POST_ID));
            }
            cursor.moveToNext();
        }
        return concate;
    }

    public void clearFavorite() {
        db = getReadableDatabase();
        db.execSQL("delete from " + MY_FAV_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

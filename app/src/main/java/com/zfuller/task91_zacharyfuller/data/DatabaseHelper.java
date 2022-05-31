package com.zfuller.task91_zacharyfuller.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import com.zfuller.task91_zacharyfuller.model.Item;
import com.zfuller.task91_zacharyfuller.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Util.TYPE + " TEXT," + Util.NAME + " TEXT," + Util.PHONE + " TEXT," + Util.DESC + " TEXT," + Util.DATE + " TEXT," +
                Util.LOCATION + " TEXT," + Util.LATITUDE + " TEXT," + Util.LONGITUDE + ")";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.TYPE, item.getType());
        contentValues.put(Util.NAME, item.getName());
        contentValues.put(Util.PHONE, item.getPhone());
        contentValues.put(Util.DESC, item.getDesc());
        contentValues.put(Util.DATE, item.getDate());
        contentValues.put(Util.LOCATION, item.getLocation());
        contentValues.put(Util.LATITUDE, item.getLatitude());
        contentValues.put(Util.LONGITUDE, item.getLongitude());
        long newRowID = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowID;
    }

    public Item fetchItem(int i) {
        Item item = new Item();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + Util.TABLE_NAME, null);
        if (cursor.moveToFirst() && cursor.getCount() >= 1) {
            do {
                if (cursor.getInt(0) == i) {
                    item.setId(cursor.getInt(0));
                    item.setType(cursor.getString(1));
                    item.setName(cursor.getString(2));
                    item.setPhone(cursor.getString(3));
                    item.setDesc(cursor.getString(4));
                    item.setDate(cursor.getString(5));
                    item.setLocation(cursor.getString(6));
                    item.setLatitude(cursor.getDouble(7));
                    item.setLongitude(cursor.getDouble(8));
                    return item;
                }
            } while (cursor.moveToNext());
        }
        return item;
    }

    public List<Item> fetchAllItem() {
        List<Item> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(0));
                item.setType(cursor.getString(1));
                item.setName(cursor.getString(2));
                item.setPhone(cursor.getString(3));
                item.setDesc(cursor.getString(4));
                item.setDate(cursor.getString(5));
                item.setLocation(cursor.getString(6));
                item.setLatitude(cursor.getDouble(7));
                item.setLongitude(cursor.getDouble(8));
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        return itemList;
    }

    public boolean deleteItem(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Util.TABLE_NAME, Util.ITEM_ID + " = " + i, null) > 0;
    }
}


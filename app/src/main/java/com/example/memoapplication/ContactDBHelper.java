package com.example.memoapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mymemo.db";
    private static final int DATABASE_VERSION = 2;

    // Database creation sql statement
    private static final String CREATE_TABLE_MEMO =
            "create table memo (_id integer primary key autoincrement, "
                    + "memoContent text, priority text, memoDate date);";

    public ContactDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_MEMO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        Log.w(ContactDBHelper.class.getName(), "Upgrading database from
// version " + oldVersion + " to "
//                + newVersion + ", which will destroy all old data");
//  db.execSQL("DROP TABLE IF EXISTS contact");
//  onCreate(db);
        try {
            db.execSQL("ALTER TABLE contact ADD COLUMN contactphoto blob");
        }
        catch (Exception e) {
            //do nothing
        }
    }


}

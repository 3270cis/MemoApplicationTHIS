package com.example.memoapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.Calendar;

public class ContactDataSource  {

    private SQLiteDatabase database;
    private ContactDBHelper dbHelper;

    public ContactDataSource(Context context) {
        dbHelper = new ContactDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertMemo(Memo m) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("_id", m.getMemoID());
            initialValues.put("memo", m.getMemoMessage());
            initialValues.put("priority", m.getPriority());
            initialValues.put("memoDate", m.getDateOfMemo().toString());


            didSucceed = database.insert("contact", null, initialValues) > 0;
        }
        catch (Exception e) {

            //MAYBE ADD A TOAST HERE?

            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }



    public Memo getSpecificMemo(int memoId) {
        Memo memo = new Memo();
        String query = "SELECT  * FROM contact WHERE _id =" + memoId;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            memo.setMemoID(cursor.getInt(0));
            memo.setMemoMessage(cursor.getString(1));
            memo.setPriority(cursor.getString(2));

            //memo.setStreetAddress(cursor.getString(2)); //THIS WOULD BE GET THE DATE


            cursor.close();
        }
        return memo;
    }


}

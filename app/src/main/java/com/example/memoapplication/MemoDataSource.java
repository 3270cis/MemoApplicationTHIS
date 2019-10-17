package com.example.memoapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

public class MemoDataSource {

    private SQLiteDatabase database;
    private MemoDBHelper dbHelper;

    private static final String DATABASE_NAME = "mymemo.db";
    private static final String DATABASE_TABLE_NAME = "memo";



    public MemoDataSource(Context context) {
        dbHelper = new MemoDBHelper(context);
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

            //initialValues.put("_id", m.getMemoID()); // the ID values are making it complicated

            initialValues.put("memoContent", m.getMemoMessage());
            initialValues.put("priority", m.getPriority());
            initialValues.put("memoDate", m.getDateOfMemo());


            didSucceed = database.insert("memo", null, initialValues) > 0;

        }
        catch (Exception e) {

            //MAYBE ADD A TOAST HERE?

            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public boolean updateMemo(int memoId, Memo m) {
        boolean didSucceedUpdate = false;

        try {
            ContentValues updateValues = new ContentValues();

            updateValues.put("memoContent",m.getMemoMessage());
            updateValues.put("priority", m.getPriority());
            updateValues.put("memoDate", m.getDateOfMemo());

            didSucceedUpdate = database.update("memo", updateValues, "_id=" + memoId, null) > 0;

        }catch (Exception ex){
            //will return false if it didn't work
        }

        return didSucceedUpdate;
    }



    //not used
    public Memo getSpecificMemo(int memoId) {
        Memo memo = new Memo();
        String query = "SELECT  * FROM memo WHERE _id =" + memoId;
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

    //not used
    public int getLastMemoId() {
        int lastId = -1;
        try {
            String query = "Select MAX(_id) from memo";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }


    //this gets the current memo ID in the database
    public int getCurrentMemoIdInDB(String memoMessage, String priority, String memoDate) {
        int currentMemoIdInDB = -1;
        try{
            String query = "SELECT _id FROM memo WHERE memoContent ='" + memoMessage+"'" + "AND memoDate ='"+ memoDate+"'";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            currentMemoIdInDB = cursor.getInt(0);
            cursor.close();

            //this one was not working correctly
//                        String columnPrioirty = "priority";
//            String columnMemoContent = "memoContent";
//
//            String result = DatabaseUtils.stringForQuery(database,
//                    "SELECT priority FROM " + DATABASE_TABLE_NAME +" WHERE memoContent ='"  + memoMessage + "' " , null);
//            currentMemoIdInDB = result;

        }catch(Exception ex) {

            currentMemoIdInDB = -1;

        }

        return currentMemoIdInDB;
    }




    public ArrayList<Memo> getMemos(String sortField) {
        ArrayList<Memo> memos = new ArrayList<Memo>();
        try {
            String query = "SELECT  * FROM memo ORDER BY  " + sortField;

            Cursor cursor = database.rawQuery(query, null);

            Memo newMemo;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newMemo = new Memo();                                          //1
                newMemo.setMemoID(cursor.getInt(0));
                newMemo.setMemoMessage(cursor.getString(1));
                newMemo.setPriority(cursor.getString(2));
                newMemo.setDateOfMemo(cursor.getString(3)); //this date is not really working?
;

                memos.add(newMemo);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            memos = new ArrayList<Memo>();
        }
        return memos;
    }

    public ArrayList<Memo> getMemosByHighPriority(String sortField) {
        ArrayList<Memo> memos = new ArrayList<Memo>();
        try {
            String query = "SELECT  * FROM memo ORDER BY CASE " + sortField +
                    "    WHEN 'High' THEN 1\n" +
                    "    WHEN 'Medium' THEN 2\n" +
                    "    WHEN 'Low' THEN 3\n" +
                    "    END ";

            Cursor cursor = database.rawQuery(query, null);

            Memo newMemo;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newMemo = new Memo();
                newMemo.setMemoID(cursor.getInt(0));
                newMemo.setMemoMessage(cursor.getString(1));
                newMemo.setPriority(cursor.getString(2));
                newMemo.setDateOfMemo(cursor.getString(3));
                ;

                memos.add(newMemo);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            memos = new ArrayList<Memo>();
        }
        return memos;
    }

    public  ArrayList<Memo> getMemosByMediumPriority(String sortField) {
        ArrayList<Memo> memos = new ArrayList<Memo>();
        try {
            String query = "SELECT  * FROM memo ORDER BY CASE " + sortField +
                    "    WHEN 'Medium' THEN 1\n" +
                    "    WHEN 'Low' THEN 2\n" +
                    "    WHEN 'High' THEN 3\n" +
                    "    END ";

            Cursor cursor = database.rawQuery(query, null);

            Memo newMemo;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newMemo = new Memo();
                newMemo.setMemoID(cursor.getInt(0));
                newMemo.setMemoMessage(cursor.getString(1));
                newMemo.setPriority(cursor.getString(2));
                newMemo.setDateOfMemo(cursor.getString(3));
                ;

                memos.add(newMemo);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            memos = new ArrayList<Memo>();
        }
        return memos;

    }

    public  ArrayList<Memo> getMemosByLowPriority(String sortField) {
        ArrayList<Memo> memos = new ArrayList<Memo>();
        try {
            String query = "SELECT  * FROM memo ORDER BY CASE " + sortField +
                    "    WHEN 'Low' THEN 1\n" +
                    "    WHEN 'Medium' THEN 2\n" +
                    "    WHEN 'High' THEN 3\n" +
                    "    END ";

            Cursor cursor = database.rawQuery(query, null);

            Memo newMemo;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newMemo = new Memo();
                newMemo.setMemoID(cursor.getInt(0));
                newMemo.setMemoMessage(cursor.getString(1));
                newMemo.setPriority(cursor.getString(2));
                newMemo.setDateOfMemo(cursor.getString(3));
                ;

                memos.add(newMemo);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            memos = new ArrayList<Memo>();
        }
        return memos;

    }



    public boolean deleteMemo(int MemoId) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("memo", "_id=" + MemoId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -return value already set to false
        }
        return didDelete;
    }

    //ORIGINAL
//
//    public ArrayList<Memo> getContacts(String sortField, String sortOrder) {
//        ArrayList<Memo> contacts = new ArrayList<Memo>();
//        try {
//            String query = "SELECT  * FROM memo ORDER BY " + sortField + " " + sortOrder;
//
//            Cursor cursor = database.rawQuery(query, null);
//
//            Memo newContact;
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                newContact = new Memo();                                          //1
//                newContact.setContactID(cursor.getInt(0));
//                newContact.setContactName(cursor.getString(1));
//                newContact.setStreetAddress(cursor.getString(2));
//                newContact.setCity(cursor.getString(3));
//                newContact.setState(cursor.getString(4));
//                newContact.setZipCode(cursor.getString(5));
//                newContact.setPhoneNumber(cursor.getString(6));
//                newContact.setCellNumber(cursor.getString(7));
//                newContact.setEMail(cursor.getString(8));
//                Calendar calendar = Calendar.getInstance();                         //2
//                calendar.setTimeInMillis(Long.valueOf(cursor.getString(9)));
//                newContact.setBirthday(calendar);
//
//                contacts.add(newContact);
//                cursor.moveToNext();
//            }
//            cursor.close();
//        }
//        catch (Exception e) {
//            contacts = new ArrayList<Contact>();
//        }
//        return contacts;
//    }


}

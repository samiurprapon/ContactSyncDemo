package me.prapon.contactsyncdemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import me.prapon.contactsyncdemo.model.Contact;

import static me.prapon.contactsyncdemo.database.Constants.COL_ID;
import static me.prapon.contactsyncdemo.database.Constants.COL_NAME;
import static me.prapon.contactsyncdemo.database.Constants.COL_PHONE;
import static me.prapon.contactsyncdemo.database.Constants.DATABASE_NAME;
import static me.prapon.contactsyncdemo.database.Constants.DATABASE_VERSION;
import static me.prapon.contactsyncdemo.database.Constants.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {


    private String createTable = new StringBuilder().append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append(" ( ")
            .append(COL_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
            .append(COL_NAME).append(" TEXT, ")
            .append(COL_PHONE).append(" TEXT ").append(");").toString();


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }


    public boolean insertContact(String name, String phone) {

        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_NAME, name);
        contentValues.put(COL_PHONE, phone);

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();

        return result != -1;
    }

    public void insertContacts(Contact contact) {

        String name = contact.getName();
        String phone = contact.getPhone();

        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_NAME, name);
        contentValues.put(COL_PHONE, phone);

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }


    public Cursor getAllContacts() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        db.close();
        return cursor;
    }

    public int deleteData(String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int value =  db.delete(TABLE_NAME, COL_ID+" = ?", new String[] {id});
        db.close();

        return value;
    }

}

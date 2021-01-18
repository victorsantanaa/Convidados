package com.example.convidados.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.convidados.constants.DataBaseConstants;

public class GuestDataBaseHelper extends SQLiteOpenHelper {

    private static final String NAME = "convidados.db";
    private static final int VERSION = 1;

    private static final String CREATE_TABLE_GUEST = "create table " + DataBaseConstants.GUEST.TABLE_NAME + " ("
            + DataBaseConstants.GUEST.COLUMNS.ID + " integer primary key autoincrement, "
            + DataBaseConstants.GUEST.COLUMNS.NAME + " text, "
            + DataBaseConstants.GUEST.COLUMNS.PRESENCE + " integer);";

    public GuestDataBaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GUEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

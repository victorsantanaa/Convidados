package com.example.convidados.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.contentcapture.DataRemovalRequest;

import com.example.convidados.constants.DataBaseConstants;
import com.example.convidados.constants.GuestConstants;
import com.example.convidados.model.GuestModel;

import java.util.ArrayList;
import java.util.List;

public class GuestRepository {


    //2º
    private static GuestRepository INSTANCE;
    private GuestDataBaseHelper mHelper;

    // Singleton - Design Pattern ( Dar um acesso por vez a classe que são sensíveis a diversos acessos
    // 1º para Singleton
    private GuestRepository(Context context) {
        this.mHelper = new GuestDataBaseHelper(context);
    }
    //3º
    public static GuestRepository getInstance(Context context) {
        if (INSTANCE == null){
            INSTANCE = new GuestRepository(context);
        }
        return INSTANCE;
    }
    //CRUD - Create, read, update, delete
    private List<GuestModel> getList(String selection, String[] selectionArgs){
        List<GuestModel> list = new ArrayList<>();

        try{

            SQLiteDatabase db = this.mHelper.getReadableDatabase();

            String table = DataBaseConstants.GUEST.TABLE_NAME;
            String[] columns = {DataBaseConstants.GUEST.COLUMNS.ID,
                    DataBaseConstants.GUEST.COLUMNS.NAME,
                    DataBaseConstants.GUEST.COLUMNS.PRESENCE};

            Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {

                while(cursor.moveToNext()){

                    int id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID));
                    String name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME));
                    int presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE));

                    list.add(new GuestModel(id, name, presence));
                }

            }

            if(cursor!= null) {
                cursor.close();
            }


            return list;
        } catch (Exception e) {
            return list;
        }
    }

    public List<GuestModel> getAll() {
        return this.getList(null, null);
    }

    public List<GuestModel> getPresents() {
        String selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?";
        String[] selectionArgs = {String.valueOf(GuestConstants.CONFIRMATION.PRESENT)};

        return this.getList(selection, selectionArgs);
    }

    public List<GuestModel> getAbsents() {
        String selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?";
        String[] selectionArgs = {String.valueOf(GuestConstants.CONFIRMATION.ABSENT)};

        return this.getList(selection, selectionArgs);
    }



    public GuestModel load(int id) {
        try {
            GuestModel guest = null;
            SQLiteDatabase db = this.mHelper.getReadableDatabase();

            String table = DataBaseConstants.GUEST.TABLE_NAME;
            String[] columns = {DataBaseConstants.GUEST.COLUMNS.ID,
                    DataBaseConstants.GUEST.COLUMNS.NAME,
                    DataBaseConstants.GUEST.COLUMNS.PRESENCE};

            String selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?";
            String[] selectionArgs = {String.valueOf(id)};

            Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME));
                int presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE));

                guest = new GuestModel(id, name, presence);
            }

            if(cursor!= null) {
                cursor.close();
            }

            return guest;

        } catch (Exception e) {
            return null;
        }
    }

    public boolean insert(GuestModel guest) {
        try {
            SQLiteDatabase db = this.mHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.getName());
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.getConfirmation());

            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values);
            db.close();

            return true;
        } catch (Exception e) {
            return false;
        }

    }
    public boolean update(GuestModel guest) {
        try {
            SQLiteDatabase db = this.mHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.getName());
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.getConfirmation());

            String where = DataBaseConstants.GUEST.COLUMNS.ID + " = ?";
            String[] args = {String.valueOf(guest.getId())};

            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, where, args);
            db.close();
            return true;
        } catch(Exception e) {
            return false;
        }
    }
    public boolean delete(int id) {
        try {
            SQLiteDatabase db = this.mHelper.getWritableDatabase();



            String where = DataBaseConstants.GUEST.COLUMNS.ID + " = ?";
            String[] args = {String.valueOf(id)};

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, where, args);
            db.close();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

}

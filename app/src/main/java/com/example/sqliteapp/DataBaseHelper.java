package com.example.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLLUM_CUSTOMER_ID = "ID";
    public static final String COLLUM_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLLUM_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLLUM_CUSTOMER_IS_ACTIVE = "IS_ACTIVE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    //Is triggered when you are using de app for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE + " ( " + COLLUM_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLLUM_CUSTOMER_NAME + " TEXT, " + COLLUM_CUSTOMER_AGE + " INT, " + COLLUM_CUSTOMER_IS_ACTIVE + " BOOL)";
        db.execSQL(createTableStatement);

    }
    //Is triggered when you need to increment your database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public  boolean addOne(CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLLUM_CUSTOMER_NAME, customerModel.getName());
        cv.put(COLLUM_CUSTOMER_AGE, customerModel.getAge());
        cv.put(COLLUM_CUSTOMER_IS_ACTIVE, customerModel.isActive());
        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        if (insert == -1){
            return false;
        }else{
            return true;
        }
    }
    public List<CustomerModel> getEveryone(){
        List<CustomerModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ CUSTOMER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do {
                int customerId = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerActive = cursor.getInt(3) == 1 ? true :false;
                CustomerModel nextCustomer = new CustomerModel(customerId, customerName, customerAge, customerActive);
                returnList.add(nextCustomer);
            }while (cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();
        return returnList;
    }
}

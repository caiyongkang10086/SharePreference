package com.cvte.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SqliteDataBase extends SQLiteOpenHelper {

    public static final String CreateBookTable = "create table Book(" +
            "id integer primary key autoincrement," +
            "author text, price real, " +
            "pages integer, " +
            "name text)";
    public static final String CreateCategoryTable = "create table Category(" +
            "id integer primary key autoincrement," +
            "author text, price real, " +
            "pages integer, " +
            "name text)";
    private Context mContext;

    public SqliteDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CreateBookTable);
        sqLiteDatabase.execSQL(CreateCategoryTable);
        Toast.makeText(mContext,"Create SQlite success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Category");
        sqLiteDatabase.execSQL("drop table if exists Book");
        onCreate(sqLiteDatabase);
    }
}

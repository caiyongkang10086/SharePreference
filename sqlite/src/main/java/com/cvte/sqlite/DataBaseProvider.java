package com.cvte.sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataBaseProvider extends ContentProvider {
    private static final String TAG = "DataBaseProvider";
    public static final int BOOK_DIR = 0;
    public static final int BOOK_ITM = 1;
    public static final int CATEGORY_DIR = 2;
    public static final int CATEGORY_ITM = 3;
    public static final String AUTHORITY = "com.cvte.sqlite.provider";
    private static UriMatcher uriMatcher;
    private SqliteDataBase sqliteDataBase;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR);
        uriMatcher.addURI(AUTHORITY, "book/#", BOOK_ITM);
        uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY, "category/#",CATEGORY_ITM);
    }


    @Override
    public boolean onCreate() {
        Log.d(TAG,"DataBaseProvider onCreate");
        sqliteDataBase = new SqliteDataBase(getContext(), "BookStore.db", null, 1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = sqliteDataBase.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                cursor = database.query("book", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BOOK_ITM:
                String bookID = uri.getPathSegments().get(1);
                cursor = database.query("book",projection, "id = ?", new String[] {bookID},
                        null, null, sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = database.query("category", projection, selection, selectionArgs
                        , null, null, sortOrder);
                break;
            case CATEGORY_ITM:
                String categoryID = uri.getPathSegments().get(1);
                cursor = database.query("category", projection, "id = ?", new String[] { categoryID }
                        ,null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        /**
         * MIME类型规则：
         * 1、必须以vnd开头
         * 2、如果内容URI以路径结尾，则后接android.cusor.dir/，如果内容URI以id结尾，则后接android.cursor.item/。
         * 3、最后接上vnd.<authority>.<path>
         */
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.cvte.sqlite.DataBaseProvider.book";
            case BOOK_ITM:
                return "vnd.android.cursor.item/vnd.com.cvte.sqlite.DataBaseProvider.book";
            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.cvte.sqlite.DataBaseProvider.category";
            case CATEGORY_ITM:
                return "vnd.android.cursor.item/vnd.com.cvte.sqlite.DataBaseProvider.category";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase database = sqliteDataBase.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
            case BOOK_ITM:
                long newBookId = database.insert("Book", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/book/" + newBookId);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITM:
                long newCategoryId = database.insert("Category", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/category/" + newCategoryId);
                break;
            default:
                break;
        }
        return uriReturn;
    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = sqliteDataBase.getWritableDatabase();
        int deleteRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                deleteRows = database.delete("Book", selection, selectionArgs);
                break;
            case BOOK_ITM:
                String bookID = uri.getPathSegments().get(1);
                deleteRows = database.delete("Book", "id = ?", new String[]{ bookID });
                break;
            case CATEGORY_DIR:
                deleteRows = database.delete("Category", selection, selectionArgs);
                break;
            case CATEGORY_ITM:
                String categoryID = uri.getPathSegments().get(1);
                deleteRows = database.delete("Category","id = ?", new String[]{ categoryID });
                break;
            default:
                break;
        }
        return deleteRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = sqliteDataBase.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                updatedRows = database.update("Book", values, selection, selectionArgs);
                break;
            case BOOK_ITM:
                String bookID = uri.getPathSegments().get(1);
                updatedRows = database.update("Book", values, "id = ?", new String[]{ bookID });
                break;
            case CATEGORY_DIR:
                updatedRows = database.update("Category", values, selection, selectionArgs);
                break;
            case CATEGORY_ITM:
                String categoryID = uri.getPathSegments().get(1);
                updatedRows = database.update("Category", values, "id = ?", new String[]{ categoryID });
                break;
            default:
                break;
        }
        return updatedRows;
    }
}

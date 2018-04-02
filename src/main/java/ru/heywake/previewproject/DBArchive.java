package ru.heywake.previewproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBArchive extends SQLiteOpenHelper {

    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "archive_news";
        public static final String COLUMN_NAME_ENTRY_ID = "id_news";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DISCRIPTION = "discription";
        public static final String COLUMN_NAME_URL = "url";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_DISCRIPTION + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_URL + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "news_archive.db";

    public DBArchive(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void insertValue(News news) {

        SQLiteDatabase db = getWritableDatabase();


        Cursor cursor = db.rawQuery("Select * From archive_news Where id_news = \"" + news.id + "\"", new String[]{});

        if (cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put(DBArchive.FeedEntry.COLUMN_NAME_ENTRY_ID, news.id);
            values.put(DBArchive.FeedEntry.COLUMN_NAME_TITLE, news.title);
            values.put(DBArchive.FeedEntry.COLUMN_NAME_DISCRIPTION, news.description);
            values.put(DBArchive.FeedEntry.COLUMN_NAME_URL, news.url);

            long newRowId;
            newRowId = db.insert(
                    "archive_news",
                    null,
                    values);

        }
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}

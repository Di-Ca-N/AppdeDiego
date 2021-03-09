package com.example.appdediego.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appdediego.models.pojo.Event;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EventDAO extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String TABLE = "events";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public EventDAO(Context context) {
        super(context, TABLE, null, VERSION);
    }

    @Override
    public void onCreate(@NotNull SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE + "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "title TEXT," +
                        "date TEXT," +
                        "description TEXT," +
                        "type TEXT" +
                        ");"
        );
    }

    @Override
    public void onUpgrade(@NotNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE + ";");
        onCreate(db);
    }

    public void save(@NotNull Event event) {
        ContentValues cv = new ContentValues();
        cv.put("title", event.getTitle());
        cv.put("date", DATE_FORMAT.format(event.getDate()));
        cv.put("description", event.getDescription());
        cv.put("type", event.getType());

        getWritableDatabase().insert(TABLE, null, cv);
    }

    public Event read(int id) {
        try {
            Cursor cursor = getReadableDatabase().query(TABLE, new String[]{"title", "date", "description", "type"},
                    "id=?", new String[]{String.valueOf(id)}, null, null, null);
            cursor.moveToFirst();

            String title = cursor.getString(cursor.getColumnIndex("title"));
            String dateString = cursor.getString(cursor.getColumnIndex("date"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            String type = cursor.getString(cursor.getColumnIndex("type"));

            Event event = new Event(id, title, DATE_FORMAT.parse(dateString), description, type);
            cursor.close();

            return event;
        } catch (Exception e) {
            return null;
        }
    }

    public void update(@NotNull Event event) {
        ContentValues cv = new ContentValues();
        cv.put("title", event.getTitle());
        cv.put("date", DATE_FORMAT.format(event.getDate()));
        cv.put("description", event.getDescription());
        cv.put("type", event.getType());

        getWritableDatabase().update(TABLE, cv, "id=?",
                new String[]{event.getId().toString()});
    }

    public void delete(@NotNull Event event) {
        getWritableDatabase().delete(TABLE, "id=?",
                new String[]{event.getId().toString()});
    }

    public ArrayList<Event> getEvents() {
        try {
            Cursor cursor = getReadableDatabase().query(
                    TABLE, new String[]{"id", "title", "date", "description", "type"},
                    null, null, null, null, null);
            ArrayList<Event> events = new ArrayList<>();

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String dateString = cursor.getString(cursor.getColumnIndex("date"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String type = cursor.getString(cursor.getColumnIndex("type"));


                events.add(new Event(id, title, DATE_FORMAT.parse(dateString), description, type));
            }
            cursor.close();
            return events;
        } catch (Exception e) {
            return null;
        }
    }
}

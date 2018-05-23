package edu.bridgewatercs.jscott.eagleassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaActionSound;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    //Database Version, Name, Table Name, Column Names
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mapMarkerInfo";
    private static final String TABLE_MAP_MARKERS = "mapMarkers";
    private static final String KEY_ID = "id";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";
    private static final String KEY_TITLE = "title";


    //begins database handler
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    //makes database
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MAP_MARKERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MAP_MARKERS
                + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_LAT + " FLOAT, "
                + KEY_LNG + " FLOAT, " + KEY_TITLE + " TEXT " + ")";
        db.execSQL(CREATE_MAP_MARKERS_TABLE);
    }



    //upgrades database from version a to version b
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAP_MARKERS);
        onCreate(db);
    }



    //adds a new map marker
    public void addMapMarker(MapMarker mapMarker) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LAT, mapMarker.getLat());
        values.put(KEY_LNG, mapMarker.getLng());
        values.put(KEY_TITLE, mapMarker.getTitle());

        db.insert(TABLE_MAP_MARKERS, null, values);
        db.close();
    }



    //gets one map marker
    public MapMarker getMapMarker(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MAP_MARKERS, new String[]{KEY_ID, KEY_LAT, KEY_LNG, KEY_TITLE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();

        MapMarker location = new MapMarker(Integer.parseInt(cursor.getString(0)), (Double.parseDouble(cursor.getString(1))), (Double.parseDouble(cursor.getString(2))), cursor.getString(3));
        return location;
    }



    //gets all the map markers
    public ArrayList<MapMarker> getAllMapMarkers() {
        ArrayList<MapMarker> mapMarkerList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MAP_MARKERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                MapMarker mapMarker = new MapMarker();
                mapMarker.setID(Integer.parseInt(cursor.getString(0)));
                mapMarker.setLat(Double.parseDouble(cursor.getString(1)));
                mapMarker.setLng(Double.parseDouble(cursor.getString(2)));
                mapMarker.setTitle(cursor.getString(3));
                mapMarkerList.add(mapMarker);
            } while (cursor.moveToNext());
        }
        return  mapMarkerList;
    }



    //gets the amount of map markers
    public int getMapMarkerCount() {
        String countQuery = "SELECT * FROM " + TABLE_MAP_MARKERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }



    //updates a map marker
    public int updateShop(MapMarker mapMarker) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LAT, mapMarker.getLat());
        values.put(KEY_LNG, mapMarker.getLng());
        values.put(KEY_TITLE, mapMarker.getTitle());

        return db.update(TABLE_MAP_MARKERS, values, KEY_ID + " = ?", new String[]{String.valueOf(mapMarker.getID())});
    }



    //deletes one mark marker
    public void deleteMapMarker(MapMarker mapMarker) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MAP_MARKERS, KEY_ID + " = ?", new String[]{String.valueOf(mapMarker.getID())});
        db.close();
    }



    //deletes all map markers
    //this is super spooky, do not run this
    public void deleteAllMapMarkers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAP_MARKERS);
        db.execSQL("VACUUM");
    }
}

package comkimhyeockjin.github.termproject;

/*
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class LocationDB extends SQLiteOpenHelper {
    public LocationDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE MONEYBOOK (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, price INTEGER, create_at TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class LocationDB extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DB_NAME = "LocationDB.db";
    private static final String TABLE_NAME = "Location";

    private SQLiteDatabase db;

    private static final String ID = "_id";
    private static final String DATE = "date";
    private static final String LNG = "lng";
    private static final String LAT = "lat";
    private static final String TIME = "time";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " integer primary key autoincrement, " +
                    DATE + " text not null, " +
                    LNG + " text not null, " +
                    LAT + " text not null, " +
                    TIME + " text not null )";

    public LocationDB(Context context) {
        super(context, DB_NAME, null, VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public synchronized void close() {
        db.close();
        super.close();
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Create
    public boolean insertInfo(String date, double lng, double lat, double time, int star, String memo, String category) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DATE, date);
        contentValues.put(LNG, lng);
        contentValues.put(LAT, lat);
        contentValues.put(TIME, time);

        return db.insert(TABLE_NAME, null, contentValues) != -1;
    }

    // Read
    public ArrayList<LocationInfo> getAllInfo() {
        ArrayList<LocationInfo> info = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, DATE, LNG, LAT, TIME}, null, null, null, null, ID + " DESC");

        if (cursor.moveToFirst()) {
            final int indexId = cursor.getColumnIndex(ID);
            final int indexDate = cursor.getColumnIndex(DATE);
            final int indexLng = cursor.getColumnIndex(LNG);
            final int indexLat = cursor.getColumnIndex(LAT);
            final int indexTime = cursor.getColumnIndex(TIME);

            do {
                int id = cursor.getInt(indexId);
                String date = cursor.getString(indexDate);
                double lng = cursor.getDouble(indexLng);
                double lat = cursor.getDouble(indexLat);
                double time = cursor.getInt(indexTime);

                info.add(new LocationInfo(id, date, lng, lat, time));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return info;
    }

    // Update
    public boolean updateInfo(LocationInfo locationInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, locationInfo.getDate());
        contentValues.put(LNG, locationInfo.getLng());
        contentValues.put(LAT, locationInfo.getLat());
        contentValues.put(TIME, locationInfo.getTime());

        String[] params = new String[]{Integer.toString(locationInfo.getId())};
        int result = db.update(TABLE_NAME, contentValues, ID + "=?", params);
        return result > 0;
    }

    // Delete
    public boolean deleteInfo(int id) {
        String[] params = new String[]{Integer.toString(id)};
        int result = db.delete(TABLE_NAME, ID + "=?", params);
        return result > 0;
    }

    public boolean deleteAll() {
        int result = db.delete(TABLE_NAME, null, null);
        return result > 0;
    }
}
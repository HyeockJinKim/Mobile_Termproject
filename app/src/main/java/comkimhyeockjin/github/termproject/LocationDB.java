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
    public LocationDB locationDB = this;
    private static final int VERSION = 1;
    private static final String DB_NAME = "LocationDB.db";
    private static final String TABLE_NAME = "Location";

    private SQLiteDatabase db;

    private static final String ID = "_id";
    private static final String DATE = "date";
    private static final String LNG = "lng";
    private static final String LAT = "lat";
    private static final String TIME = "time";
    private static final String STAR = "star";
    private static final String MEMO = "memo";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " integer primary key autoincrement, " +
                    DATE + " text not null, " +
                    LNG + " text not null, " +
                    LAT + " text not null, " +
                    TIME + " text not null, " +
                    STAR + " text not null, " +
                    MEMO + " text not null )";

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
    public boolean insertInfo(String date, double lng, double lat, double time, int star, String memo) {
        ContentValues cv = new ContentValues();

        cv.put(DATE, date);
        cv.put(LNG, lng);
        cv.put(LAT, lat);
        cv.put(TIME, time);
        cv.put(STAR, star);
        cv.put(MEMO, memo);

        return db.insert(TABLE_NAME, null, cv) != -1;
    }

    // Read
    public ArrayList<LocationInfo> getAllInfo() {
        ArrayList<LocationInfo> info = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, new String[]{ID, DATE, LNG, LAT, TIME, STAR, MEMO}, null, null, null, null, ID + " DESC");

        if (c.moveToFirst()) {
            final int indexId = c.getColumnIndex(ID);
            final int indexDate = c.getColumnIndex(DATE);
            final int indexLng = c.getColumnIndex(LNG);
            final int indexLat = c.getColumnIndex(LAT);
            final int indexTime = c.getColumnIndex(TIME);
            final int indexStar = c.getColumnIndex(STAR);
            final int indexMemo = c.getColumnIndex(MEMO);

            do {
                int id = c.getInt(indexId);
                String date = c.getString(indexDate);
                double lng = c.getDouble(indexLng);
                double lat = c.getDouble(indexLat);
                double time = c.getInt(indexTime);
                int star = c.getInt(indexStar);
                String memo = c.getString(indexMemo);

                info.add(new LocationInfo(id, date, lng, lat, time, star, memo));
            } while (c.moveToNext());
        }
        c.close();

        return info;
    }

    // Update
    public boolean updateInfo(LocationInfo i) {
        ContentValues cv = new ContentValues();
        cv.put(DATE, i.getDate());
        cv.put(LNG, i.getLng());
        cv.put(LAT, i.getLat());
        cv.put(TIME, i.getTime());
        cv.put(STAR, i.getStar());
        cv.put(MEMO, i.getMemo());

        String[] params = new String[]{Integer.toString(i.getId())};
        int result = db.update(TABLE_NAME, cv, ID + "=?", params);
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
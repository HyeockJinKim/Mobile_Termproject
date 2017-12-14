package comkimhyeockjin.github.termproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by user on 2017-12-11.
 */


/**
 * 장소마다의 데이터가 저장.
 */
public class PlaceDB extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DB_NAME = "PlaceDB.db";
    private static final String TABLE_NAME = "Place";

    private SQLiteDatabase db;

    private static final String ID = "_id";
    private static final String LNG = "lng";
    private static final String LAT = "lat";
    private static final String TIME = "time";
    private static final String STAR = "star";
    private static final String NAME = "name";
    private static final String MEMO = "memo";
    private static final String CATE = "category";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " integer primary key autoincrement, " +
                    LNG + " text not null, " +
                    LAT + " text not null, " +
                    TIME + " text not null, " +
                    STAR + " text not null, " +
                    NAME + " text not null, " +
                    MEMO + " text not null, " +
                    CATE + " text not null )";

    public PlaceDB(Context context) {
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
    public boolean insertInfo(double lng, double lat, int time, int star, String name, String memo, String category) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(LNG, lng);
        contentValues.put(LAT, lat);
        contentValues.put(TIME, time);
        contentValues.put(STAR, star);
        contentValues.put(NAME, name);
        contentValues.put(MEMO, memo);
        contentValues.put(CATE, category);

        return db.insert(TABLE_NAME, null, contentValues) != -1;
    }

    // Read
    public ArrayList<PlaceInfo> getAllInfo() {
        ArrayList<PlaceInfo> info = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, LNG, LAT, TIME, STAR, NAME, MEMO, CATE}, null, null, null, null, ID + " DESC");

        if (cursor.moveToFirst()) {
            final int indexId = cursor.getColumnIndex(ID);
            final int indexLng = cursor.getColumnIndex(LNG);
            final int indexLat = cursor.getColumnIndex(LAT);
            final int indexTime = cursor.getColumnIndex(TIME);
            final int indexStar = cursor.getColumnIndex(STAR);
            final int indexName = cursor.getColumnIndex(NAME);
            final int indexMemo = cursor.getColumnIndex(MEMO);
            final int indexCATE = cursor.getColumnIndex(CATE);

            do {
                int id = cursor.getInt(indexId);
                double lng = cursor.getDouble(indexLng);
                double lat = cursor.getDouble(indexLat);
                int time = cursor.getInt(indexTime);
                int star = cursor.getInt(indexStar);
                String name = cursor.getString(indexName);
                String memo = cursor.getString(indexMemo);
                String cate = cursor.getString(indexCATE);

                info.add(new PlaceInfo(id, lng, lat, time, star, name, memo, cate));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return info;
    }

    // Update
    public boolean updateInfo(PlaceInfo placeInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LNG, placeInfo.getLng());
        contentValues.put(LAT, placeInfo.getLat());
        contentValues.put(TIME, placeInfo.getTime());
        contentValues.put(STAR, placeInfo.getStar());
        contentValues.put(NAME, placeInfo.getName());
        contentValues.put(MEMO, placeInfo.getMemo());
        contentValues.put(CATE, placeInfo.getCategory());

        String[] params = new String[]{Integer.toString(placeInfo.getId())};
        int result = db.update(TABLE_NAME, contentValues, ID + "=?", params);
        return result > 0;
    }

    public boolean checkInfo(double lat, double lng) {
        Cursor cursor = db.rawQuery("select * from "+ TABLE_NAME+" where lat = " + lat + " and lng = "+lng+";", null);
        return cursor.moveToFirst();
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

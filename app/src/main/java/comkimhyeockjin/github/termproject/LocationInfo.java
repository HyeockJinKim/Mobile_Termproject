package comkimhyeockjin.github.termproject;

/**
 * Created by user on 2017-11-27.
 */

public class LocationInfo {

    public int id;
    public String date;
    public double lng;
    public double lat;
    public double time;
    public int star;
    public String memo;

    public LocationInfo(int id, String date, double lng, double lat, double time, int star, String memo) {
        this.id = id;
        this.date = date;
        this.lng = lng;
        this.lat = lat;
        this.time = time;
        this.star = star;
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}

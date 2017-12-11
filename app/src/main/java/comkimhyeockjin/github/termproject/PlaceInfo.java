package comkimhyeockjin.github.termproject;

/**
 * Created by user on 2017-11-27.
 */

public class PlaceInfo {

    public int id;
    public double lng;
    public double lat;
    public double time;
    public int star;
    public String memo;
    public String category;

    public PlaceInfo(int id, double lng, double lat, double time, int star, String memo, String category) {
        this.id = id;
        this.lng = lng;
        this.lat = lat;
        this.time = time;
        this.star = star;
        this.memo = memo;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getTime() {
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


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.memo = category;
    }

}

package comkimhyeockjin.github.termproject;

/**
 * Created by user on 2017-11-27.
 */

/**
 * 시간 순으로 적히는 것.
 */
public class LocationInfo {

    public int id;
    public String date;
    public double lng;
    public double lat;
    public String friend;
    public int time;


    public LocationInfo(int id, String date, double lng, double lat, String friend, int time) {
        this.id = id;
        this.date = date;
        this.lng = lng;
        this.lat = lat;
        this.friend = friend;
        this.time = time;
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

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

}

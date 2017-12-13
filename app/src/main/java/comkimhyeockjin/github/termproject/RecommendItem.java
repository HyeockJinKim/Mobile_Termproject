package comkimhyeockjin.github.termproject;

/**
 * Created by user on 2017-11-11.
 */

public class RecommendItem {
    private String locationName;
    private double distance;
    private String category;
    private double lat;
    private double lng;

    public RecommendItem(String locationName, double distance, String category, double lat, double lng) {
        this.locationName = locationName;
        this.distance = distance;
        this.category = category;
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getLocationName() {
        return locationName;
    }

    public double getDistance() {
        return distance;
    }

    public String getCategory() {
        return category;
    }
}

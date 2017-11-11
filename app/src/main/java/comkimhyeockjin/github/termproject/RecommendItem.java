package comkimhyeockjin.github.termproject;

/**
 * Created by user on 2017-11-11.
 */

public class RecommendItem {
    private String locationName;
    private double distance;
    private String category;

    public RecommendItem(String locationName, double distance, String category) {
        this.locationName = locationName;
        this.distance = distance;
        this.category = category;
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

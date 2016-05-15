package wehack.tune.walkbuddies.Model;

import android.location.Location;

/**
 * Created by aixin on 5/14/16.
 */
public class RouteLocation {
    private Location start;
    private Location dest;

    public RouteLocation(double startLat, double startLng, double endLat, double endLng) {
        this.start = new Location("startingLocation");
        this.start.setLatitude(startLat);
        this.start.setLongitude(startLng);

        this.dest = new Location("destLocation");
        this.dest.setLatitude(endLat);
        this.dest.setLongitude(endLng);
    }

    public Location getStart() {
        Location ret = new Location("startingLocation");
        ret.setLatitude(this.start.getLatitude());
        ret.setLongitude(this.start.getLongitude());
        return ret;
    }

    public Location getDest() {
        Location ret = new Location("destLocation");
        ret.setLatitude(this.dest.getLatitude());
        ret.setLongitude(this.dest.getLongitude());
        return ret;
    }
}

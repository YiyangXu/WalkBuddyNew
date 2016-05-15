package wehack.tune.walkbuddies.Model;

import wehack.tune.walkbuddies.Model.*;

/**
 * Created by aixin on 5/14/16.
 */
public class User {
    private String name;
    private int phone;
    private RouteLocation location;

    public User(String name, int phone, RouteLocation location) {
        this.name = name;
        this.phone = phone;
        this.location = location;
    }

    public String getName() {
        return this.name;
    }

    public int getphone() {
        return this.phone;
    }

    public RouteLocation getLocation() {
        return this.location;
    }

    public void setphone (int phone) {
        this.phone = phone;
    }

    public void setLocation (RouteLocation location) {
        if (location == null) {
            throw new IllegalArgumentException("Illegal location");
        }
        this.location = location;
    }
}
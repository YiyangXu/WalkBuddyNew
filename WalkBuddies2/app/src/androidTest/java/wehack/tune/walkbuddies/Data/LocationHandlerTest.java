package wehack.tune.walkbuddies.Data;

import android.support.test.runner.AndroidJUnit4;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import wehack.tune.walkbuddies.Model.*;
import wehack.tune.walkbuddies.Data.*;

/**
 * Created by aixin on 5/14/16.
 */
@RunWith(AndroidJUnit4.class)
public class LocationHandlerTest {
    private static final int TIMEOUT = 2000;

    @Test
    public void testGetAll() {
        RouteLocation current = new RouteLocation(47.653580, 122.305888, 47.663049, 122.306320);
        List<User> listings = new ArrayList<>();

        // adding the first user
        RouteLocation temp = new RouteLocation(47.653580, 122.305888, 47.663049, 122.306320);
        User a = new User("Cyndi", 1231231123, temp);
        listings.add(a);

        // adding the second user
        temp = new RouteLocation(39.9042, 116.4074, 31.2304, 121.4737);
        User b = new User("Holu", 2222222222, temp);
        listings.add(b);

        List<User> result = getPossibleBuddies(current, listings);
    }


}

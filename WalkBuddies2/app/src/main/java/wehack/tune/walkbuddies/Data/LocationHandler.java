package wehack.tune.walkbuddies.Data;

import wehack.tune.walkbuddies.Model.*;

import android.location.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aixin on 5/14/16.
 */
public class LocationHandler {
    private final double SEARCHING_STARTING_RANGE = 0.5;
    private final double SEARCHING_DEST_RANGE = 1;
    private final double RATIO_TO_MILES = 0.000189393939;

    private final String GOOGLE_SERVICE_CALL_PREFIX = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial";
    private final String GOOGLE_SERVICE_API_KEY = "AIzaSyDQp80zPXzffOGWQ-JbwS3pnqRaHYCE6gM";


    public List<User> getPossibleBuddies(RouteLocation current, List<User> listings) {
        List<User> res = new ArrayList<>();
        for (User u: listings) {
            if (isPotentialBuddy(current, u))
                res.add(u);
        }
        return res;
    }

    private boolean isPotentialBuddy(RouteLocation current, User potential) {
        boolean closeStart = isWithinRange(current.getStart(),
                potential.getLocation().getStart(), SEARCHING_STARTING_RANGE);
        boolean closeDest = isWithinRange(current.getDest(),
                potential.getLocation().getDest(), SEARCHING_DEST_RANGE);
        return closeStart && closeDest;
    }

    private boolean isWithinRange(Location a, Location b, double range) {
        String req = GOOGLE_SERVICE_CALL_PREFIX +
                "&origins=" + a.getLatitude() + "," + a.getLongitude() +
                "&destinations=" + b.getLatitude() + "," + b.getLatitude() +
                "&mode=walking" +
                "&key=" + GOOGLE_SERVICE_API_KEY;
        JSONObject ret = reqToJSONHelper(req);
        if (ret == null)
            throw new IllegalArgumentException("Ooops, something wrong with the params");
        double dist = getDistHelper(ret);
        return dist <= range;
    }

    private JSONObject reqToJSONHelper(String req) {
        JSONObject ret = null;

        try {
            URL url = new URL(req);
            URLConnection urlConn = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            ret = new JSONObject(sb.toString());
        } catch (MalformedURLException e) {
            System.err.println("Invalid url: " + req);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Cannot open connection");
            e.printStackTrace();
        } catch (JSONException e) {
            System.err.println("Connot parse to a JSON");
            e.printStackTrace();
        }
        return ret;
    }

    private double getDistHelper(JSONObject obj) {
        String dist = "";
        try {
            obj = (JSONObject) obj.get("rows");
            JSONArray arr = obj.getJSONArray("elements");
            obj = arr.getJSONObject(0);
            obj = (JSONObject) obj.get("distance");
            dist = obj.getString("text");
        } catch (JSONException e) {
            System.err.println("Failed Parsing JSON Returned Object");
            e.printStackTrace();
        }

        if (dist.equals(""))
            throw new IllegalArgumentException("Failed Parsing JSON Returned Object");
        return convertMiles(dist);
    }

    private double convertMiles(String dist) {
        String[] arr = dist.split(" ");
        if (arr[1].equals("mi"))
            return Integer.parseInt(arr[0]);
        return Integer.parseInt(arr[0]) * RATIO_TO_MILES;
    }
}

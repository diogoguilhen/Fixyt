package fixyt.fixyt;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;


public class CalculadorETA {
    public String obterETA(double latitude, double longitude, double prelatitute, double prelongitude) throws IOException, JSONException {
        String resultado_em_segundos = "";
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="
                + latitude + "," + longitude + "&destination=" + prelatitute
                + "," + prelongitude + "&sensor=false&units=metric&key=AIzaSyD5uvH4kSQWM60dsrfk80zcUrqxYqDQ3nc";
        String tag[] = { "text" };

       JSONObject json = readJsonFromUrl(url);
     //   final JSONObject json = new JSONObject(result);
        JSONArray routeArray = json.getJSONArray("routes");
        JSONObject routes = routeArray.getJSONObject(0);

        JSONArray newTempARr = routes.getJSONArray("legs");
        JSONObject newDisTimeOb = newTempARr.getJSONObject(0);

        JSONObject distOb = newDisTimeOb.getJSONObject("distance");
        JSONObject timeOb = newDisTimeOb.getJSONObject("duration");

        Log.i("Distance :", distOb.getString("text"));
        Log.i("Time :", timeOb.getString("text"));

        return timeOb.getString("text");
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }




}




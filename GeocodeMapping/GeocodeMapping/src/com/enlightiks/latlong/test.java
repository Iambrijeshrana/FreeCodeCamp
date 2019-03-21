/*package com.enlightiks.latlong;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class test {

	public static GeoPoint getGeoPointFromAddress(String locationAddress) {
        GeoPoint locationPoint = null;
        String locationAddres = locationAddress.replaceAll(" ", "%20");
        String str = "http://maps.googleapis.com/maps/api/geocode/json?address="
                + locationAddres + "&sensor=true";

        String ss = readWebService(str);
        JSONObject json;
        try {

            String lat, lon;
            json = new JSONObject(ss);
            JSONObject geoMetryObject = new JSONObject();
            JSONObject locations = new JSONObject();
            JSONArray jarr = json.getJSONArray("results");
            int i;
            for (i = 0; i < jarr.length(); i++) {
                json = jarr.getJSONObject(i);
                geoMetryObject = json.getJSONObject("geometry");
                locations = geoMetryObject.getJSONObject("location");
                lat = locations.getString("lat");
                lon = locations.getString("lng");

                locationPoint = Utils.getGeoPoint(Double.parseDouble(lat),
                        Double.parseDouble(lon));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locationPoint;
    }
}
*/
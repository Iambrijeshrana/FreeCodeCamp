import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
/**
 *
 * @param lng
 * @param lat
 * @return
 */

class MapAdd{

	public static void main(String[] args) {
		MapAdd mapadd = new MapAdd();
		String lng = "77.6544856";
        String lat = "12.8498481";
		try {
			String add = mapadd.getAddressByGpsCoordinates(lng, lat);
			System.out.println(add);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	private String getAddressByGpsCoordinates(String lng, String lat)
            throws MalformedURLException, IOException, org.json.simple.parser.ParseException {
         
        URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng="
                + lat + "," + lng + "&key=AIzaSyCIlAwHGKgwd3iJ2DmoOIOU2tj3h-E3G64&sensor=true");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String formattedAddress = "";
 System.out.println("start...");
        try {
        	System.out.println("url : "+url);
            InputStream in = url.openStream();
            System.out.println(in.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
            String result = line;
            System.out.println("result +result : "+line);
            while ((line = reader.readLine()) != null) {
                result += line;
            }
 
            JSONParser parser = new JSONParser();
            JSONObject rsp = (JSONObject) parser.parse(result);
 
            if (rsp.containsKey("results")) {
                JSONArray matches = (JSONArray) rsp.get("results");
                JSONObject data = (JSONObject) matches.get(0); //TODO: check if idx=0 exists
                formattedAddress = (String) data.get("formatted_address");
                System.out.println("formattedAddress : "+formattedAddress);
            }
 
            return "";
        } finally {
            urlConnection.disconnect();
            return formattedAddress;
        }
    }
}
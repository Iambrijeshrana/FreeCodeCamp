package com.enlightiks.latlong;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;


public class AddressConverter {
 /*
  * Geocode request URL. Here see we are passing "json" it means we will get
  * the output in JSON format. You can also pass "xml" instead of "json" for
  * XML output. For XML output URL will be
  * "http://maps.googleapis.com/maps/api/geocode/xml";
  */
static String latitude=null;
static String longitude=null;
 private static final String URL = "https://maps.googleapis.com/maps/api/geocode/json";

 /*
  * Here the fullAddress String is in format like
  * "address,city,state,zipcode". Here address means "street number + route"
  * .
  */
 public GoogleResponse convertToLatLong(String fullAddress) throws IOException {

  /*
   * Create an java.net.URL object by passing the request URL in
   * constructor. Here you can see I am converting the fullAddress String
   * in UTF-8 format. You will get Exception if you don't convert your
   * address in UTF-8 format. Perhaps google loves UTF-8 format. :) In
   * parameter we also need to pass "sensor" parameter. sensor (required
   * parameter) — Indicates whether or not the geocoding request comes
   * from a device with a location sensor. This value must be either true
   * or false.
   */
  URL url = new URL(URL + "?address="
    + URLEncoder.encode(fullAddress, "UTF-8") + "&key=GoogleAPIKey&sensor=false");
  //System.out.println("fullAddress is : "+fullAddress);
  //System.out.println("url address is : "+url);
  // Open the Connection
  URLConnection conn = url.openConnection();
  
  InputStream in = conn.getInputStream() ;
  ObjectMapper mapper = new ObjectMapper();
  GoogleResponse response = (GoogleResponse)mapper.readValue(in,GoogleResponse.class);
  in.close();
  return response;
  

 }
 
 public GoogleResponse convertFromLatLong(String latlongString) throws IOException {

  /*
   * Create an java.net.URL object by passing the request URL in
   * constructor. Here you can see I am converting the fullAddress String
   * in UTF-8 format. You will get Exception if you don't convert your
   * address in UTF-8 format. Perhaps google loves UTF-8 format. :) In
   * parameter we also need to pass "sensor" parameter. sensor (required
   * parameter) — Indicates whether or not the geocoding request comes
   * from a device with a location sensor. This value must be either true
   * or false.
   */
  URL url = new URL(URL + "?latlng="
    + URLEncoder.encode(latlongString, "UTF-8") + "&key=GoogleAPIKEY&sensor=true");
  // Open the Connection 
  //https://maps.googleapis.com/maps/api/geocode/json?latlng=18.92038860%2C72.83013059999999&key=AIzaSyCkGM5ptGgv-xmUOTRZzRU_fM603GqedwU&sensor=false
  //System.out.println("url is : "+url);
  URLConnection conn = url.openConnection();

  InputStream in = conn.getInputStream() ;
  ObjectMapper mapper = new ObjectMapper();
  GoogleResponse response = (GoogleResponse)mapper.readValue(in,GoogleResponse.class);
  in.close();
  return response;

 }
 
 public static void main(String[] args) throws IOException {
  
 GoogleResponse res = new AddressConverter().convertToLatLong("560068"+", India");
  
  if(res.getStatus().equals("OK"))
  {
   for(Result result : res.getResults())
   {
   latitude = result.getGeometry().getLocation().getLat();
   longitude = result.getGeometry().getLocation().getLng();
   System.out.println("Lattitude of address - "  +result.getGeometry().getLocation().getLat());
   System.out.println("Longitude of address - " + result.getGeometry().getLocation().getLng());
   System.out.println("Location is " + result.getGeometry().getLocation_type());
   
  //  break;
   }
  }
  else
  {
   System.out.println("else block status : "+res.getStatus());
  }

 // https://maps.googleapis.com/maps/api/geocode/json?latlng=12.8498481,77.6544856&key=GoogleApiKey
	  
	//  GoogleResponse rs = new 
  
  

  
	// GoogleResponse res1 = new AddressConverter().convertToLatLong("560068");
//  GoogleResponse res1 = new AddressConverter().convertFromLatLong("12.8498481,77.6544856");  
  GoogleResponse res1 = new AddressConverter().convertFromLatLong(latitude+","+longitude);
  if(res1.getStatus().equals("OK"))
  {
   for(Result result : res1.getResults())
   {

    System.out.println("address is :"  +result.getFormatted_address());
    Pattern zipPattern = Pattern.compile("(\\d{6})");
	Matcher zipMatcher = zipPattern.matcher(result.getFormatted_address());
	if (zipMatcher.find()) {
	    String zip = zipMatcher.group(1);
	    System.out.println("Pin Code - "+zip);
	//    break;
	}
   }
  }
  else
  {
   System.out.println(res1.getStatus());
  }
  
 }
 

}

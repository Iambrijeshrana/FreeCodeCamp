package com.enlightiks.latlong;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 
 * 
 * 
 * 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleResponse {
	@JsonIgnore
	private String place_id;
 private Result[] results ;
 private String status ;
 public Result[] getResults() {
  return results;
 }
 public void setResults(Result[] results) {
  this.results = results;
 }
 public String getStatus() {
  return status;
 }
 public void setStatus(String status) {
  this.status = status;
 }
public String getPlace_id() {
	return place_id;
}
public void setPlace_id(String place_id) {
	this.place_id = place_id;
}
 

}
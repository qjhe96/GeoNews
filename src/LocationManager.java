
import java.util.*;
import java.io.*;	
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;


public class LocationManager {

	private static final String URL = "https://maps.googleapis.com/maps/api/geocode/json";
	public String city;
	public String state;
	public String address;
	public String API_Key = "AIzaSyBmHuH6XmnaDjVTk7UYBs4xk5y8Cw09rUw";
	
	public LocationManager(String location){

		address = location;
		if (location.contains(",")){
		List<String> locationList = Arrays.asList(location.split(", "));
		city = locationList.get(0);
		state = locationList.get(1);
		}
		else{
			state = address;
		}
	}
	
	public String getJSONGeoEncode(String address) throws MalformedURLException, IOException {
	URL url = new URL(URL + "?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=false" + "&key=" + API_Key);
	URLConnection conn = url.openConnection();
	ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
	IOUtils.copy(conn.getInputStream(), output);
	output.close();
	return output.toString();
}	
	
	public static void main(String[] args) throws MalformedURLException, IOException{
		LocationManager test = new LocationManager("California");
		System.out.println(test.city);
		System.out.println(test.state);

		String json = test.getJSONGeoEncode(test.address);
		System.out.println(json);
		ParseJSON parse = new ParseJSON(json);
		
		/*parse.parseCoordinate(parse.output);
		System.out.println(parse.latitude);
		System.out.println(parse.longitude);*/
		
		double[] coords;
		try {
			coords = parse.parseLatLong();
			System.out.println("" + coords[0] + " " + coords[1]);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

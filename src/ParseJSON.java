import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ParseJSON {

	public double latitude;
	public double longitude;
	public String output;
	
	public ParseJSON(String output){
		this.output = output;	
	}
	
	public double[] parseLatLong() throws JSONException {
		JSONObject locationObj = new JSONObject(this.output);
		JSONArray results = locationObj.getJSONArray("results");
		JSONObject firstResult = results.getJSONObject(0);
		JSONObject geometry = firstResult.getJSONObject("geometry");
		JSONObject location = geometry.getJSONObject("location");
		return new double[]{location.getDouble("lat"), location.getDouble("lng")};
	}
	
	public String[] parseCoordinate(String s){
		String[] lines = output.split("\n");
		String lat = null;
		String lng = null;
		for (int i = 0; i < lines.length; i++){
			if ("\"location\" : {".equals(lines[i].trim()))
            {
                lat = getOrdinate(lines[i+1]);
                lng = getOrdinate(lines[i+2]);
                this.latitude = Double.parseDouble(lat);
                this.longitude = Double.parseDouble(lng);
                break;
            }
		}
		return new String[] {lat, lng};
	}
	
	public static String getOrdinate(String s){
		String[] split = s.trim().split(" ");
		if (split.length < 1){
			return null;
		}
		String ord = split[split.length-1];
		if (ord.endsWith(",")){
			ord = ord.substring(0, ord.length() - 1);
		}
		Double.parseDouble(ord);
		return ord;
	}
}

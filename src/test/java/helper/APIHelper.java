package helper;

import java.util.ArrayList;
import java.util.HashMap;


public class APIHelper {
	
	public static HashMap<String, String> getHeaderGetMovies(){
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user", "admin");
		map.put("integer", "1");
		map.put("description" ,"The user session id or empty if not logged in");		
		return map;		
	}
	
	public static String getLoginBody(ArrayList<String> excelRow){
		String body="{\r\n" + 
				"   \"username\": \""+excelRow.get(0)+"\",\r\n" + 
				"    \"password\": \""+excelRow.get(1)+"\"  \r\n" + 
				"}";
		return body;		
	}
	

}

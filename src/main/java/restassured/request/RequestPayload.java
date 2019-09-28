package restassured.request;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

/* Class Decription - Class contains all the properties/parameters required to send a REST API request. 
 * 					  Constructor call will create payload for a request based of the Parameter passed. 
 * 					  [Get | Post | Put]     
 * Created by - Sachin Ahuja
 * Created on - 15th March
 * Modified by
 * Modified on
 * */
public class RequestPayload {
   public String resource;
   public HashMap<String, String> qParams;
   public HashMap<String, String> params;
   public HashMap<String, String> headers;
   public String body;
   public ContentType contentType;
   public String cookieName;
   public RequestSpecification httpRequest;
   public String authorizationType;
   public String requestType;
   
    
   public RequestPayload(String requestType) {
	   resource="";
	   headers=new HashMap<String, String>();
	   cookieName="";
	   
	   authorizationType="";
	   this.requestType=requestType;
	   qParams=new HashMap<String, String>();
	   body="";
	   params=new HashMap<String, String>();
   }
}
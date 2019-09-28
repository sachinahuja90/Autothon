package apiTest;

import java.util.ArrayList;

import org.apache.commons.httpclient.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import helper.APIHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import restassured.request.RequestPayload;
import restassured.request.RestAssuredRequest;


public class TestAPI {

	static String tokenID="";
	@BeforeClass
	public void initBaseURL() {
		RestAssured.baseURI="https://autothon-nagarro-backend-b11.azurewebsites.net/";
	}
	@Test
	public void getMovies() {
		
		RequestPayload getM=new RequestPayload("get");
		getM.headers=APIHelper.getHeaderGetMovies();
		getM.resource="movies";
		Response res=RestAssuredRequest.sendAPIRequest(getM);
		Assert.assertEquals(res.statusCode(), HttpStatus.SC_OK);
		
	}
	
	
	@Test(groups = {"user"},dataProvider = "Login Data",dataProviderClass =DataProvider.class)
	public void login(ArrayList<String> headers,ArrayList<String> excelRow) {		
		RequestPayload getM=new RequestPayload("post");
		getM.resource="login";
		getM.body=APIHelper.getLoginBody(excelRow);
		Response res=RestAssuredRequest.sendAPIRequest(getM);
		Assert.assertEquals(res.statusCode(), HttpStatus.SC_OK);	
		tokenID=res.jsonPath().get("id");
	}
	
	@Test(groups = {"user"},dataProvider = "Login Data",dataProviderClass =DataProvider.class)
	public void logout(ArrayList<String> headers,ArrayList<String> excelRow) {		
		RequestPayload getM=new RequestPayload("get");
		getM.resource="logout";
		Response res=RestAssuredRequest.sendAPIRequest(getM);
		Assert.assertEquals(res.statusCode(), HttpStatus.SC_OK);	
	}
}

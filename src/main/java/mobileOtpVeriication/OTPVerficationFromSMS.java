package mobileOtpVeriication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import propertyReader.PropertyReader;
import reportLogger.ReportFactory;
import com.twilio.rest.api.v2010.account.Message;



/**
 * This class is to fetch OTP from email.
 * 
 * @author sagar.malik
 * 
 */

public class OTPVerficationFromSMS{

	private static final String SMS_RECEIVE_FREE_URL = "https://smsreceivefree.com/country/usa";
    private static final String SMS_MESSAGE_LIST_URL = "https://smsreceivefree.com/info/";
    private static final String NUMBER_CSS_CLASS = "numbutton";
    private static final String TABLE_CSS_SELECTOR = "table.msgTable > tbody > tr";
    private static final String HREF = "href";
    private static final String HTML_TD = "td";
    private static final String FORWARD_SLASH = "/";
    private static final Pattern PATTERN = Pattern.compile("/info/(\\d+)/");	
    static HashMap<String, String> smsPropertyMap;

    public String getNumber() throws IOException {
        return Jsoup.connect(SMS_RECEIVE_FREE_URL)           //access the site
                    .get()
                    .body()                                  //get html body
                    .getElementsByClass(NUMBER_CSS_CLASS)    //get all number elements
                    .stream()
                    .map(e -> e.attr(HREF))                  //get href attr value
                    .map(PATTERN::matcher)                   //regex to find the number in the url
                    .filter(Matcher::find)                   //if there is number
                    .findAny()                               //pick any
                    .map(m -> m.group(1))                    //get the number
                    .orElseThrow(IllegalStateException::new);
    }
    public String getMessage(final String phoneNumber, final String fromPhoneNumber) throws IOException {
        return Jsoup.connect(SMS_MESSAGE_LIST_URL.concat(phoneNumber).concat(FORWARD_SLASH))   //access the site
                    .get()
                    .body()                                                                        //get html body
                    .select(TABLE_CSS_SELECTOR)
                    .stream()
                    .map(tr -> tr.getElementsByTag(HTML_TD))                                       //get all cells
                    .filter(tds -> tds.get(0)
                                            .text()
                                            .trim()
                                            .startsWith(fromPhoneNumber))                          //if the number starts with given number
                    .findFirst()                                                                   //find first match
                    .map(tds -> tds.get(2))                                                        //pick 3rd cell
                    .map(td -> td.text().trim())                                                   //get cell value
                    .orElseThrow(IllegalStateException::new);
    }
	
	public static void main(String args[]) throws FileNotFoundException, IOException {
		OTPVerficationFromSMS sms = new OTPVerficationFromSMS();
//		String phoneNumber = sms.getNumber();
		String code = sms.getMessage("18187946209", "1234567");
//		String str="";
//		try {
//			smsPropertyMap=new PropertyReader().getProperties(System.getProperty("user.dir")+"\\src\\main\\java\\mobileOtpVeriication\\otp.properties");
//			
//			ReportFactory.getInstance().debug("OTP extracted : "+str);
//		}
//		catch (Exception e) {
//			ReportFactory.getInstance().debug("Error while getting OTP : "+e.getMessage());
//		}
//		return str;
	}
}
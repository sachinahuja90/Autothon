package mobileOtpVeriication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
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

public class OTPVerficationFromSMSTwilio{

	int messageCount;
	int unreadMsgCount;
	String emailSubject;
	Message emailMessage;
	static HashMap<String, String> smsPropertyMap;
	private static final String TO_PHONE_NUMBER = "+12566266031";
	static String ACCOUNT_SID="ACf5963f7ccf7883178e8de3fecae6e9fd";
	
	public static void main(String args[]) throws FileNotFoundException, IOException {
		
		String str="";
		try {
			smsPropertyMap=new PropertyReader().getProperties(System.getProperty("user.dir")+"\\src\\main\\java\\mobileOtpVeriication\\otp.properties");
			Twilio.init(smsPropertyMap.get("ACCOUNT_SID"), smsPropertyMap.get("AUTH_TOKEN")) ;
//			sendSMS(smsPropertyMap.get("ACCOUNT_SID"), smsPropertyMap.get("AUTH_TOKEN")) ;
			String str1=getMessages(smsPropertyMap.get("ACCOUNT_SID"), smsPropertyMap.get("AUTH_TOKEN")) ;
			ReportFactory.getInstance().debug("OTP extracted : "+str1);
		}
		catch (Exception e) {
			e.printStackTrace();
			ReportFactory.getInstance().debug("Error while getting OTP : "+e.getMessage());
		}
//		return str;
	}
//
	private static void sendSMS(String ACCOUNT_SID, String AUTH_TOKEN) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+12566266031"),
                new com.twilio.type.PhoneNumber("+12566266031"),
                "Hi there!")
            .create();
        System.out.println(message.getSid());
	}
	
	public static String getMessages(String ACCOUNT_SID, String AUTH_TOKEN) {
		return getMessages()
                .filter(m -> m.getDirection().compareTo(Message.Direction.INBOUND) == 0)
                .filter(m -> m.getTo().equals(TO_PHONE_NUMBER))
                .map(Message::getBody)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
	
	
	private static Stream<Message> getMessages(){
        ResourceSet<Message> messages = Message.reader(ACCOUNT_SID).read();
        return StreamSupport.stream(messages.spliterator(), false);
    }
	
	
	
	
	
	
	
}
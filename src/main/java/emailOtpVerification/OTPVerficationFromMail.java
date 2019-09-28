package emailOtpVerification;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;


import propertyReader.PropertyReader;
import reportLogger.ReportFactory;

/**
 * This class is to fetch OTP from email.
 * 
 * @author sagar.malik
 * 
 */

public class OTPVerficationFromMail{
//	String hostName = "smtp.mail.yahoo.com";
	
//	
//	static String hostName = "smtp.gmail.com";
//	static String username = "sahuja014@gmail.com";
//	static String password = "Mycantos@1";
	int messageCount;
	int unreadMsgCount;
	String emailSubject;
	Message emailMessage;
	static HashMap<String, String> emailPropertyMap;

	
	public static String main(String args[]) throws FileNotFoundException, IOException {
		String str="";
		try {
			emailPropertyMap=new PropertyReader().getProperties(System.getProperty("user.dir")+"\\src\\main\\java\\emailOtpVerification\\otp.properties");			
			str=new OTPVerficationFromMail().mailReader(emailPropertyMap.get("hostName"), emailPropertyMap.get("username"), emailPropertyMap.get("password")) ;
			ReportFactory.getInstance().debug("OTP extracted : "+str);
		}
		catch (Exception e) {
			ReportFactory.getInstance().debug("Error while getting OTP : "+e.getMessage());
		}
		return str;
	}

	/**
	 * mailReader
	 * This function is to extract the OTP from the mail.
	 * @param hostName The host name of the gmail/any email account
	 * @param username The user name of the gmail/any email account
	 * @param password The password of the gmail/any email account
	 * @return string This function returns the value in string 
	 */
	public String mailReader(String hostName, String username, String password) {
		String otp ="";
		boolean flag=false;
	    Properties sysProps = System.getProperties();
	    sysProps.setProperty(emailPropertyMap.get("systemProperty"), emailPropertyMap.get("protocol"));
	    

	    try {
	        Session session = Session.getInstance(sysProps, null);
	        Store store = session.getStore();	       
	        store.connect(hostName, username, password);
	        Folder emailInbox = store.getFolder("INBOX");
	        emailInbox.open(Folder.READ_WRITE);
	        messageCount = emailInbox.getMessageCount();
	        System.out.println("Total Message Count: " + messageCount);
	        unreadMsgCount = emailInbox.getNewMessageCount();
	        System.out.println("Unread Emails count:" + unreadMsgCount);
//	        Message emailMessages[] = emailInbox.getMessages(messageCount-10,messageCount);
	        
	        Message[] emailMessages = emailInbox.search(
					new FlagTerm(new Flags(Flags.Flag.SEEN), false));
	        for(int i=emailMessages.length-1;i>=0;i--) {
	        	Message emailMessage=emailMessages[i];
		        emailSubject = emailMessage.getSubject();
		        System.out.println(emailSubject);
		        Pattern linkPattern = Pattern.compile("Your email verification OTP is (\\d{6})"); // here you need to define regex as per you need
		        Matcher pageMatcher =
		                linkPattern.matcher(getTextFromMessage(emailMessage));
	
		        emailMessage.setFlag(Flags.Flag.SEEN, true);
		        while (pageMatcher.find()) {
		            System.out.println("Found OTP " + pageMatcher.group(1));
		            otp = pageMatcher.group(1);
		            flag=true;
		        }	        
		        
		        if(flag==true) {
		        	break;
		        }
		        
	        }
	        emailInbox.close(true);
	        store.close();
	        
	    } catch (Exception mex) {
	        mex.printStackTrace();
	    }
	    return otp;
	}
	
	private String getTextFromMessage(Message message) throws MessagingException, IOException {
	    String result = "";
	    if (message.isMimeType("text/plain")) {
	        result = message.getContent().toString();
	    } else if (message.isMimeType("multipart/*")) {
	        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
	        result = getTextFromMimeMultipart(mimeMultipart);
	    }
	    return result;
	}

	private String getTextFromMimeMultipart(
	        MimeMultipart mimeMultipart)  throws MessagingException, IOException{
	    String result = "";
	    int count = mimeMultipart.getCount();
	    for (int i = 0; i < count; i++) {
	        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
	        if (bodyPart.isMimeType("text/plain")) {
	            result = result + "\n" + bodyPart.getContent();
	            break; // without break same text appears twice in my tests
	        } else if (bodyPart.getContent() instanceof MimeMultipart){
	            result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
	        }
	    }
	    return result;
	}
}
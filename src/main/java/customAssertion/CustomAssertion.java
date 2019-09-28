package customAssertion;

import java.util.List;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Lists;
import reportLogger.ReportFactory;

public class CustomAssertion extends Assertion {
	  
	  private List<String> assert_messages = Lists.newArrayList();
	   
	    @Override
	    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
	    	ReportFactory.getInstance().error(assertCommand.getMessage());
	    	assert_messages.add("OnlyOnAssertFailure:" + assertCommand.getMessage());
	      
	    }
	/*
	 * @Override public void onAssertSuccess(IAssert<?> assertCommand) { String
	 * str=assertCommand.getMessage().replace("doesn't","");
	 * ReportLogger.error(str); assert_messages.add("OnlyOnAssertSuccess:" + str); }
	 */
	    
	    
	    
	}
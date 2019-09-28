package reportLogger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import keywords.GUIKeyword;

public class ReportFactory {

	private static ReportFactory instance = null;

	public static HashMap<String, String> reportPropertyMap;
	public static final Logger LOGGER = Logger.getLogger(ReportFactory.class);
	public static String reportFolder;
	public static String archiveFolder;

	private ReportFactory() {

	}

	public static ReportFactory getInstance() {
		if (instance == null) {
			instance = new ReportFactory();
		}
		return instance;
	}

	private static ThreadLocal<ExtentReports> extentReport = new ThreadLocal<ExtentReports>();

	private static ThreadLocal<ExtentHtmlReporter> extentHtmlReporter = new ThreadLocal<ExtentHtmlReporter>();

	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

	/*
	 * Function Decription - Function will log info message on HTML report and
	 * Logger file Created by - Sachin Ahuja Created on - 13th April Modified by
	 * Modified on
	 */

	public final void generateReport(String browser) throws Exception {
		try {
			String reportPath = reportFolder + "/" + browser + "_report.html";
			extentHtmlReporter.set(new ExtentHtmlReporter(reportPath));
			extentHtmlReporter.get().config().setChartVisibilityOnOpen(true);
			extentHtmlReporter.get().config().setDocumentTitle(reportPropertyMap.get("htmlReportTitle"));
			extentHtmlReporter.get().config().setReportName(reportPropertyMap.get("htmlReportTitle"));
			extentHtmlReporter.get().config().setTestViewChartLocation(ChartLocation.TOP);
			extentHtmlReporter.get().config().setTheme(Theme.STANDARD);
			extentHtmlReporter.get().config().setTimeStampFormat(reportPropertyMap.get("TimeStampFormat"));
			extentReport.set(new ExtentReports());
			extentReport.get().attachReporter(extentHtmlReporter.get());
			LOGGER.info("HTML report created : " + reportPath);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * Function Decription - Function will log info message on HTML report and
	 * Logger file Created by - Sachin Ahuja Created on - 13th April Modified by
	 * Modified on
	 */
	public final void info(String msg) {
		extentTest.get().log(Status.INFO, msg);
		LOGGER.info(msg);
	}

	/*
	 * Function Decription - Function will log error message on Logger file Created
	 * by - Sachin Ahuja Created on - 13th April Modified by Modified on
	 */
	public final void error(String msg) {
		extentTest.get().log(Status.ERROR, msg);
		LOGGER.error(msg);

	}

	/*
	 * Function Decription - Function will report Testcase as Pass on Html report
	 * and Logger file Created by - Sachin Ahuja Created on - 13th April Modified by
	 * Modified on
	 */

	public final void pass(ITestResult result) {
		extentTest.get().log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASSED ", ExtentColor.GREEN));
		LOGGER.info(result.getName() + " PASSED ");
	}

	/*
	 * Function Decription - Function will report Testcase as fail on Html report
	 * and Logger file Created by - Sachin Ahuja Created on - 13th April Modified by
	 * Modified on
	 */
	public final void fail(ITestResult result) {
		extentTest.get().log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " FAILED ", ExtentColor.RED));
		extentTest.get().fail(result.getThrowable());
		LOGGER.error(result.getName() + " FAILED ");
		StringWriter sw = new StringWriter();
		result.getThrowable().printStackTrace(new PrintWriter(sw));
		LOGGER.error(sw.toString());
		sw = null;
		try {
			String path = new GUIKeyword().getScreenshot(result.getName() + "_FailedScreenShot");
			extentTest.get().addScreenCaptureFromPath(path);
		} catch (IOException e) {
			LOGGER.info("Failed to upload Screenshot in extent report");
		}
	}

	/*
	 * Function Decription - Function will report Testcase as Skipped on Html report
	 * and Logger file Created by - Sachin Ahuja Created on - 13th April Modified by
	 * Modified on
	 */
	public final void skipped(ITestResult result) {
		extentTest.get().log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " SKIPPED ", ExtentColor.ORANGE));
		extentTest.get().skip(result.getThrowable());
		LOGGER.error(result.getName() + " SKIPPED ");
	}

	/*
	 * Function Decription - Function will publish HTML Report Created by - Sachin
	 * Ahuja Created on - 13th April Modified by Modified on
	 */
	public final void printReport(String browser) {
		try {
			extentReport.get().flush();
			LOGGER.info("HTML report saved for browser : " + browser + " at " + reportFolder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Function Decription - Function will create a new Extent Test Created by -
	 * Sachin Ahuja Created on - 13th April 
	 * Modified by 
	 * Modified on
	 */

	public final void newTest(String method, ITestResult result) {
		extentTest.set(extentReport.get().createTest(method));
		LOGGER.info("New testcase :" + method);
		ReportFactory.getInstance().info("Testcase Description : " + result.getMethod().getDescription());
	}

	/*
	 * Function Decription - Function will log error message on Logger file Created
	 * by - Sachin Ahuja Created on - 13th April Modified by Modified on
	 */
	public final void debug(String msg) {
		LOGGER.debug(msg);

	}
}

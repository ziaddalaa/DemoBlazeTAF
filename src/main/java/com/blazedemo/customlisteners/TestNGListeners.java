package com.blazedemo.customlisteners;

import com.blazedemo.FileUtils;
import com.blazedemo.drivers.WebDriverProvider;
import com.blazedemo.media.ScreenrecordsManager;
import com.blazedemo.media.ScreenshotsManager;
import com.blazedemo.utils.dataReader.PropertyReader;
import com.blazedemo.utils.logs.LogsManager;
import com.blazedemo.utils.report.AllureAttachmentManager;
import com.blazedemo.utils.report.AllureConstants;
import com.blazedemo.utils.report.AllureEnvironmentManager;
import com.blazedemo.utils.report.AllureReportGenerator;
import com.blazedemo.validations.Validation;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;

public class TestNGListeners implements IExecutionListener, IInvokedMethodListener, ITestListener {
    public void onExecutionStart() {
        LogsManager.info("TestNG execution started");
        cleanTestOutputDirs();
        LogsManager.info("Test output directories cleaned");
        createTestOutputDirs();
        LogsManager.info("Test output directories created");
        PropertyReader.loadProperties();
        LogsManager.info("Properties loaded");
        AllureEnvironmentManager.setEnvironmentVariables();
        LogsManager.info("Allure environment variables set");
    }

    public void onExecutionFinish() {
        AllureReportGenerator.generateReports(false);
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(true);
        AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        LogsManager.info("TestNG execution finished");
    }

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            ScreenrecordsManager.startRecording();
            LogsManager.info("Starting test: " + testResult.getMethod().getMethodName());
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        WebDriver driver = null;
        if(method.isTestMethod()){
            ScreenrecordsManager.stopRecording(testResult.getName());
            Validation.assertAll();
            if(testResult.getInstance() instanceof WebDriverProvider provider)
                driver = provider.getWebDriver();

            switch (testResult.getStatus()) {
                case ITestResult.SUCCESS -> ScreenshotsManager.takeFullPageScreenshot(driver, "passed-" + testResult.getName());
                case ITestResult.FAILURE -> ScreenshotsManager.takeFullPageScreenshot(driver, "failed-" + testResult.getName());
                case ITestResult.SKIP -> ScreenshotsManager.takeFullPageScreenshot(driver, "skipped-" + testResult.getName());
        }
            AllureAttachmentManager.attachLogs();
            AllureAttachmentManager.attachRecords(testResult.getName());
    }
    }


    public void onTestSuccess(ITestResult result) {
        LogsManager.info("Test passed: " + result.getMethod().getMethodName());
    }

    public void onTestFailure(ITestResult result) {
        LogsManager.info("Test failed: " + result.getMethod().getMethodName());
    }

    public void onTestSkipped(ITestResult result) {
        LogsManager.info("Test skipped: " + result.getMethod().getMethodName());
    }

    //Cleaning and creating Dirs (logs, screenshots, recordings, allure-results)
    private void cleanTestOutputDirs(){
        FileUtils.cleanDir(AllureConstants.RESULTS_FOLDER.toFile());
        FileUtils.cleanDir(new File(ScreenshotsManager.SCREENSHOTS_PATH));
        FileUtils.cleanDir(new File(ScreenrecordsManager.RECORDINGS_PATH));
        FileUtils.cleanDir(new File(LogsManager.LOGS_PATH));
    }

    private void createTestOutputDirs(){
        FileUtils.createDir(ScreenshotsManager.SCREENSHOTS_PATH);
        FileUtils.createDir(ScreenrecordsManager.RECORDINGS_PATH);
    }

}

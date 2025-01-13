package web;

import lombok.extern.log4j.Log4j2;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.util.concurrent.TimeUnit;

@Log4j2
public class TestListener implements ITestListener {

    public void onStart(ITestContext iTestContext) {
        System.out.println("START TESTING " + iTestContext.getName());
    }

    public void onFinish(ITestContext iTestContext) {
        System.out.println("FINISHED TESTING " + iTestContext.getName());
    }

    public void onTestStart(ITestResult iTestResult) {
        log.info("====== STARTING TEST {} ======", iTestResult.getName());
    }

    public void onTestSuccess(ITestResult iTestResult) {
        log.info("====== FINISHED TEST {} Duration {} sec. ====== ", iTestResult.getName(), getExecutionTimeInSeconds(iTestResult));
    }

    public void onTestFailure(ITestResult iTestResult) {
        log.info("====== FAILED TEST {} Duration {} sec. ====== ", iTestResult.getName(), getExecutionTimeInSeconds(iTestResult));
    }

    public void onTestSkipped(ITestResult iTestResult) {
        log.info("====== SKIPPING TEST {} ======", iTestResult.getName());
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(iTestResult);
        String result = "FailedButWithinSuccessPercentage";
        Reporter.log(String.format("-------------Result: %s-------------", result), true);
        log.info("Test {} failed but within success percentage.", iTestResult.getName());
    }

    private long getExecutionTimeInSeconds(ITestResult iTestResult) {
        long durationMillis = iTestResult.getEndMillis() - iTestResult.getStartMillis();
        if (durationMillis < 0) {
            throw new IllegalArgumentException("End time cannot be earlier than start time.");
        }
        return TimeUnit.MILLISECONDS.toSeconds(durationMillis);
    }
}


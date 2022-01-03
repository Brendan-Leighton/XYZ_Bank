package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    int counter = 0;
    int retries = 4;

    /**
     * Returns true if the test method has to repeat.
     * Returns false if the test method reached it's "retries" limit.
     *
     * @param iTestResult The result of the test method that just ran.
     * @return true if the test method has to be retried, false otherwise.
     */
    @Override
    public boolean retry(ITestResult iTestResult) {
        if (counter < retries) {
            counter++;
            return true;
        }
        return false;
    }
}

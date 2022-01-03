package listeners;
// JAVA
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
// TEST-NG
import org.testng.annotations.ITestAnnotation;
import org.testng.IAnnotationTransformer;

public class AnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}

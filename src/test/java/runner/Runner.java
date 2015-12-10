package runner;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testNG = new TestNG();
        testNG.addListener(tla);
        List<String> suites = new ArrayList<String>();
        suites.add(".//src//test//java//suits//testng.xml");
        testNG.setTestSuites(suites);
        testNG.run();
    }
}

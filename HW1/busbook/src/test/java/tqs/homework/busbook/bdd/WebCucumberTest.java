package tqs.homework.busbook.bdd;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.ConfigurationParameter;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("tqs/homework/busbook")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "tqs.homework.busbook.bdd")
public class WebCucumberTest {

}
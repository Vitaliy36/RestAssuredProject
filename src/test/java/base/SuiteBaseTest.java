package base;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.aeonbits.owner.ConfigFactory;
import org.rest.properties.EnvironmentConfig;
import org.testng.annotations.BeforeSuite;

public class SuiteBaseTest {
    @BeforeSuite
    public void setUpConfiguration() {
        EnvironmentConfig environmentConfig = ConfigFactory.create(EnvironmentConfig.class);
        RestAssured.baseURI = environmentConfig.baseUri();
        RestAssured.basePath = environmentConfig.basePath();

        // This class replaces .log().all()
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}

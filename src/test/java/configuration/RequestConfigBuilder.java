package configuration;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;

public class RequestConfigBuilder {

    public RequestSpecBuilder getRequestSpecBuilder() {
        return new RequestSpecBuilder()
                .setConfig(RestAssuredConfig.config().objectMapperConfig(objectMapperConfig().defaultObjectMapperType(ObjectMapperType.GSON))) // Ustawiamy Object Mapper, w naszym wypadku jest to oczywiście GSON
                .setContentType("application/json");
    }

    public static RequestSpecification getDefaultRequestSpecification() {
        return new RequestConfigBuilder().getRequestSpecBuilder().build();
    }}

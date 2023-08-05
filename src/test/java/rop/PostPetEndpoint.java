package rop;

import configuration.RequestConfigBuilder;
import org.apache.http.HttpStatus;
import org.rest.pojo.Pet;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class PostPetEndpoint extends BaseEndpoint<PostPetEndpoint, Pet> {

    private Pet pet;

    @Override
    protected Type getModelType() {
        return Pet.class;
    }

    @Override
    public PostPetEndpoint sendRequest() {

//        switch (method) {
//            case "post":
//                response = given().spec(RequestConfigBuilder.getDefaultRequestSpecification()).body(pet).when().post("pet");
//                break;
//            case "put":
//                response = given().spec(RequestConfigBuilder.getDefaultRequestSpecification()).body(pet).when().put("pet");
//                break;
//        }
        response = given().spec(RequestConfigBuilder.getDefaultRequestSpecification()).body(pet).when().post("pet");
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public PostPetEndpoint setPet(Pet pet) {
        this.pet = pet;
        return this;
    }
}

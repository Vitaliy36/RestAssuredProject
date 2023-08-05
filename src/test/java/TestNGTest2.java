import base.SuiteBaseTest;
import configuration.RequestConfigBuilder;
import org.rest.pojo.Category;
import org.rest.pojo.Pet;
import org.rest.pojo.Tag;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import rop.PostPetEndpoint;

import java.util.Collections;

import static io.restassured.RestAssured.given;

public class TestNGTest2 extends SuiteBaseTest {

    private String globalPetId;

    @Test(priority = 1)
    @Parameters({"petId", "petName", "petCategoryId", "petCategoryName", "petStatus", "petTagId", "petTagName"})
    public void post(String petId, String petName, String petCategoryId, String petCategoryName, String petStatus, String petTagId, String petTagName) {
        Pet petRequest = createRequest(petId, petName, petCategoryId, petCategoryName, petStatus, petTagId, petTagName);

        Pet petResponse = new PostPetEndpoint().setPet(petRequest).sendRequest().assertRequestSuccess().getResponseModel();
        Assert.assertEquals(petResponse.getId(), petRequest.getId(), "Pet ID");
        Assert.assertEquals(petResponse.getName(), petRequest.getName(), "Pet Name");
        Assert.assertEquals(petResponse.getCategory().getId(), petRequest.getCategory().getId(), "PetCategoryId");
        Assert.assertEquals(petResponse.getStatus(), petRequest.getStatus(), "Pet Status");
        Assert.assertEquals(petResponse.getTags().get(0).getName(), petRequest.getTags().get(0).getName(), "Pet TagName");
        globalPetId = petResponse.getId().toString();
        System.out.println("!!!!!!!!!!!Pet Id: " + globalPetId);

    }
    private Pet createRequest(String petId, String petName, String petCategoryId, String petCategoryName,
                              String petStatus, String petTagId, String petTagName) {
        Category category = new Category();
        category.setId(Integer.valueOf(petCategoryId));
        category.setName(petCategoryName);

        Tag tag = new Tag();
        tag.setId(Integer.valueOf(petTagId));
        tag.setName(petTagName);

        Pet petRequest = new Pet();
        petRequest.setId(Integer.valueOf(petId));
        petRequest.setName(petName);
        petRequest.setCategory(category);
        petRequest.setTags(Collections.singletonList(tag));
        petRequest.setStatus(petStatus);
        return petRequest;
    }

    @Test(priority = 2)
//    @Parameters({"petId", "petName", "petCategoryId", "petCategoryName", "petStatus", "petTagId", "petTagName"})
//    public void get() {
    public void get() {
        System.out.println("????????GetPetId: " + globalPetId);
        Pet pet = given()
                .spec(RequestConfigBuilder.getDefaultRequestSpecification())
                .pathParam("petId", globalPetId)
                .when()
                .get("/pet/{petId}")
                .then()
                .extract()
                .as(Pet.class);
        Assert.assertEquals(pet.getId(), Integer.valueOf(globalPetId), "Get Pet Id");
    }


//    @Test(priority = 3)
//    @Parameters({"petId", "petName", "petCategoryId", "petStatus", "petTagId", "petCategoryName", "petTagName"})
//    public void put(String petId, String petName, String petCategoryId, String petCategoryName, String petStatus, String petTagId, String petTagName) {
//        Pet petRequest = createRequest(petId, petName, petCategoryId, petCategoryName, petStatus, petTagId, petTagName);
//
//        Pet petResponse = new PostPetEndpoint().setPet(petRequest).sendRequest().assertRequestSuccess().getResponseModel();
//       Pet pet = given()
//                .when()
//                .body(createRequest(petId, petName, petCategoryId, petCategoryName, petStatus, petTagId, petTagName))
//                .put("/pet")
//                .then()
//                .extract()
//                .as(Pet.class);
//        Assert.assertEquals(petResponse.getId(), petRequest.getId(), "Pet ID");
//        Assert.assertEquals(petResponse.getName(), petRequest.getName(), "Pet NAME");
//        Assert.assertEquals(petResponse.getCategory().getId(), petRequest.getCategory().getId(), "Pet CatID");
//        Assert.assertEquals(petResponse.getStatus(), petRequest.getStatus(), "Pet Status");
//        Assert.assertEquals(petResponse.getPhotoUrls().get(0), petRequest.getPhotoUrls().get(0), "Pet PhotoURL");

//    }

//    @AfterMethod
//    public void delete() {
//       given()
//                .when()
//                .delete("/pet/" + globalPetId)
//                .then()
//                .statusCode(200)
//                .extract()
//                .as(Pet.class);
//    }
}

// RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
//         RestAssured.responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).build();
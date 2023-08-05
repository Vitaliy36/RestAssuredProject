import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.rest.pojo.Category;
import org.rest.pojo.Pet;
import org.rest.pojo.Tag;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;

public class DataProviderTest {

    @BeforeClass
    public void setUpConfiguration() {
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "/v2";

        // This class replaces .log().all()
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).build();
    }

    @DataProvider(name = "createPet")
    public Object [][] localDataProvider() {
        return new Object[][] {
                {"6", "Bee", "available", "10"}
        };
    }

    @Test(dataProvider = "createPet")
    public void test1(String petId, String petName, String petStatus, String petCategoryId) {
        Category category = new Category();
       // category.setId(1);
        category.setId(Integer.valueOf(petCategoryId));
        category.setName("Cats");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Cats-category");

        Pet request = new Pet();
        request.setId(Integer.valueOf(petId));
        request.setName(petName);
       // request.setStatus("available");
        request.setStatus(petStatus);
        request.setCategory(category);
        request.setTags(Collections.singletonList(tag));  // singleElement - static method return an immutable list containing only the specific object.
        request.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));

        Pet response = given()
                .body(request)
                .when()
                .post("/pet")
                .then()
                .extract()
                .as(Pet.class);
        Assert.assertEquals(response.getId(), request.getId(), "Pet ID");
        Assert.assertEquals(response.getName(), request.getName(), "Pet NAME");
        Assert.assertEquals(response.getCategory().getId(), request.getCategory().getId(), "Pet CatID");
        Assert.assertEquals(response.getStatus(), request.getStatus(), "Pet Status");
        Assert.assertEquals(response.getPhotoUrls().get(0), request.getPhotoUrls().get(0), "Pet PhotoURL");
    }
}

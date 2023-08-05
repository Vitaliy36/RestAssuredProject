import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.assertj.core.api.SoftAssertions;
import org.rest.pojo.Category;
import org.rest.pojo.Pet;
import org.rest.pojo.Tag;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;

public class DeserializTest {
//    Random random = new Random();
//    int randomNumber = random.nextInt(1000) + 1;

    @BeforeClass
    public void setUpConfiguration() {
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "/v2";

        // This class replaces .log().all()
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).build();
    }

    @Test
    public void test1() {
        Category category = new Category();
        category.setId(1);
        category.setName("Cats");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Cats-category");

        Pet request = new Pet();
        request.setId(3);
        request.setName("Bobik");
        request.setStatus("available");
        request.setCategory(category);
        request.setTags(Collections.singletonList(tag));  // singleElement - static method return an immutable list containing only the specific object.
        request.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));

        // For header and cookie I used google website
        Pet response = given()
//                .header("header", "Authorization", "SAPISIDHASH 1685817221_db84ccd1986c0155127d44f5124e55175ddb5024")
//                .header("header", "Authorization", "123")
//                .cookie("cookie", "Cookie", "__Secure-3PAPISID=tfg3DcSxY-4clqQU/AFfGMCZrwUnqBfKl-; VISITOR_INFO1_LIVE=VLGTQTCOxcQ; LOGIN_INFO=AFmmF2swRgIhAIXEGGQURsp7YjLKCWABteIqsorFAn8J0fWtT4HohSh2AiEA0AAxuBDHX8QgGzV9YKKH_MCX4TGZvyOKTqWoLQR79yQ:QUQ3MjNmeHBjWndHNmZWRlNlbTNudmNLc3VCcDZpdHRwZDRXMTBZV3RCTEF2X3pNQTRLX3Q5RWRra0dLQUlMVHlaeFc4SkhLNWx4c0R0TU1UV2dLVDN3QVFrSkJPLTJKNlBHUl9tZll2UkN6aUg5aGp0eWNzYzBzM2w1SUpUMXBSRzZ5bXZfNWpvT0g3eXQ1QTd1Y0o2NlBPZEI3RWctZURB; YSC=jeByD-xWKT4; __Secure-3PSID=WQjQX8Z-Vx7VglCb_THRH0PHwtpoCa2xpA7uoLfGxKTCc304nur-9AAP18sWWQehXW0ZTw.; __Secure-3PSIDCC=AP8dLtw6UVXFgOtp7ZFln3iXcZMi9pyg1Phol7r8WZo8tieRGmjoiHJtJpTjKExxjGKUdYl59y4")
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
        // H/W Each test(7) from BasicTest to do Deserialization.
        // Postman Post and must be deleted
    }

    @Test
    public void test2() {
//        RestAssured.baseURI = "https://petstore.swagger.io/#/pet/getPetById";
//        RestAssured.given().contentType(ContentType.JSON).when().get().as(Pet.class);
       Pet response = given()
                .pathParam("petId", 3)
                .contentType("application/json")
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .extract()
                .as(Pet.class);
       Assert.assertEquals(response.getId(), 3, "Pet ID");
       Assert.assertEquals(response.getName(), "Bobik", "Pet ID");
       Assert.assertEquals(response.getCategory().getId(), 1, "Pet ID");
    }

    @Test
    public void test3() {
        Category category = new Category();
        category.setId(1);
        category.setName("Spiders");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Spiders-category");

        Pet pet = new Pet();
        pet.setId(4);
        pet.setName("Yellow Banana");
        pet.setStatus("available");
        pet.setCategory(category);
        pet.setTags(Collections.singletonList(tag));
        pet.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));

        given()
                .body(pet)
                .contentType("application/json")
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .extract()
                .as(Pet.class);
    }

    @Test
    public void paramTest1() {
       Pet[] pet = given()
                .queryParam("status", "available")
                .contentType(ContentType.JSON)
                .when()
                .get("/pet/findByStatus")
                .then()
                .statusCode(200)
                .extract()
                .as(Pet[].class);
       // Here we're redoing table into list because with list easier working
       // List<Pet> list = Arrays.asList(pet);
       // Assert.assertFalse(list.isEmpty()); // бібіліотека - перевірка з TestNG
        SoftAssertions softAssertions = new SoftAssertions(); // бібліотека - перевірка з AssertJ
        softAssertions.assertThat(pet).hasSizeGreaterThan(0);
        softAssertions.assertThat(pet).hasSizeGreaterThan(0);
        softAssertions.assertThat(pet).hasSizeGreaterThan(0);
        softAssertions.assertThat(pet.length).isEqualTo(18);
        softAssertions.assertAll();
    }

    @Test
    public void paramTest2() {
        Pet petName = given()
                .pathParam("petName", 3)
                .contentType("application/json")
                .when()
                .get("/pet/{petName}")
                .then()
                .statusCode(200)
                .extract()
                .as(Pet.class);
        SoftAssertions softAssertions = new SoftAssertions();
       // softAssertions.assertThat(petName);
        softAssertions.assertThat(petName.getId()).isEqualTo(3);
        softAssertions.assertThat(petName.getName()).isEqualTo("Bobik")
                .isNotEqualTo("Sharik")
                .startsWith("Bo")
                .endsWith("ik")
                .isEqualToIgnoringCase("bobik")
                .hasSize(5);
        softAssertions.assertAll();
    }

    @Test
    public void paramTest3() {
        Pet pet = given()
                .pathParam("pet", 4)
                .contentType("application/json")
                .when()
                .get("/pet/{pet}")
                .then()
                .statusCode(200)
                .extract()
                .as(Pet.class);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(pet.getId()).isNotEqualTo(2);
        softAssertions.assertThat(pet.getName()).isNotEqualTo("Bobik");
        softAssertions.assertThat(pet.getStatus()).isNotEqualTo("unavailable");
        softAssertions.assertThat(pet.getName()).doesNotEndWith("Bobik");
        softAssertions.assertAll();
    }
}

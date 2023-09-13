import org.rest.pojo.Category;
import org.rest.pojo.Pet;
import org.rest.pojo.Tag;
import org.testng.annotations.Test;
import java.util.Collections;
import static io.restassured.RestAssured.given;
public class BasicTest {

    // Serialization -> when we transform classes into Json.
    // Deserialization -> when we transform Json into classes.
    @Test
    public void test1() {
        given()
                .pathParam("petId", "123")
                .when()
                .get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId}")
                .then()
                .statusCode(200)
                .log()
                .ifValidationFails();
    }
    @Test
    public void test2() {
        Category category = new Category();
        category.setId(1);
        category.setName("Cats");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Cats-category");

        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Bobik");
        pet.setStatus("available");
        pet.setCategory(category);
        pet.setTags(Collections.singletonList(tag));  // singleElement - static method return an immutable list containing only the specific object.
        pet.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));

       given()
                .log()
                .all()
                .body(pet)
                .contentType("application/json")
                .when()
                .post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then()
                .log()
                .all()
                .statusCode(200);
    }
    @Test
   public void test3() {
        given()
                .pathParam("petId2", "1")
                .contentType("application/json")
                .when()
                .get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId2}")
                .then()
                .log()
                .all()
                .statusCode(200);
    }
    @Test
   public void test4() {
        Category category = new Category();
        category.setId(1);
        category.setName("Spiders");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Spiders-category");

        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Yellow Banana");
        pet.setStatus("available");
        pet.setCategory(category);
        pet.setTags(Collections.singletonList(tag));
        pet.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));

        given()
                .log()
                .all()
                .body(pet)
                .contentType("application/json")
                .when()
                .post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then()
                .log()
                .all()
                .statusCode(200);
    }
    @Test
   public void test5() {
        given()
                .pathParam("petId1", "1")
                .contentType("application/json")
                .when()
                .get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId1}")
                .then()
                .log()
                .all()
                .statusCode(200);
    }
    @Test
   public void test6() {
        Category category = new Category();
        category.setId(1);
        category.setName("Spider");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Spiders-category");

        Pet pet = new Pet();
        pet.setId(2);
        pet.setName("Black Widow");
        pet.setStatus("available");
        pet.setCategory(category);
        pet.setTags(Collections.singletonList(tag));
        pet.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));

                given()
                        .log()
                        .all()
                        .body(pet)
                        .contentType("application/json")
                        .when()
                        .post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/")
                        .then()
                        .log()
                        .all()
                        .statusCode(200);
    }
    @Test
    public void test7() {
        given()
                .pathParam("petId2", "2")
                .contentType("application/json")
                .when()
                .get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId2}")
                .then()
                .log()
                .all()
                .statusCode(200);
    }
    @Test
   public void test8() {
        given()
                .log()
                .all()
                .contentType("application/json")
                .when()
                .delete("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/1")
                .then()
                .log()
                .all()
                .statusCode(200);
    }
}

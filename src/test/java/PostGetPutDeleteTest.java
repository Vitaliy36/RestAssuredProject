import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class PostGetPutDeleteTest implements Animal {
    //create one test and use all instructions(4) in one test. Test has to be passed.
    //create 5th instruction make sure that pet is deleted

    //GET
    //POST
    //GET
    //PUT
    //GET
    //DELETE
    //GET

    @Test
    public void test() {

        RestAssured
                .given()
                .pathParam("petID", "111")
                .log()
                .all()
                .contentType("application/json")
                .when()
                .get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petID}")
                .then()
                .log()
                .all()
                .statusCode(200);

        RestAssured
                .given()
                .log()
                .all()
                .contentType("application/json")
                .body(pet2)
                .when()
                .post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/")
                .then()
                .log()
                .all()
                .statusCode(200);

        RestAssured
                .given()
                .log()
                .all()
                .contentType("application/json")
                .body(pet)
                .when()
                .put("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/")
                .then()
                .log()
                .all()
                .statusCode(200);

        RestAssured
                .given()
                .log()
                .all()
                .contentType("application/json")
                .when()
                .delete("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/222")
                .then()
                .log()
                .all()
                .statusCode(200);

    }
}

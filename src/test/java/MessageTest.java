import io.restassured.RestAssured;
import org.rest.Message;
import org.testng.annotations.Test;

public class MessageTest {

    @Test
    public void test1() {
        Message message = new Message();
        message.setMessage("Hello people");
        RestAssured
                .given()
                .log()
                .all()
                .contentType("application/json")
                .body(message)
                .when()
                .post("http://booking.com/message");


        Message message1 = new Message();
        message1.setMessage("Getting out of bed is almost always wrong decision");
        RestAssured
                .given()
                .log()
                .all()
                .contentType("application/json")
                .body(message1)
                .when()
                .post("http://booking.com/message");

    }



}

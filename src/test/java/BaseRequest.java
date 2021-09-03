import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class BaseRequest {
    public ValidatableResponse getMethod(String path, int code){

        ValidatableResponse requestSpecification = given()
                .baseUri(ReadProperties.getProperty("host"))
                .basePath(path)
                .contentType(ContentType.JSON)
                .when().get().then().statusCode(code);
        return requestSpecification;
    }

    public ValidatableResponse postMethod(String path,String req, int code){

        ValidatableResponse requestSpecification = given()
                .baseUri(ReadProperties.getProperty("host"))
                .basePath(path)
                .contentType(ContentType.JSON)
                .and().body(req)
                .when().post().then().statusCode(code);
        return requestSpecification;
    }

    public String getUser(int usNumber){
        String user = ReadProperties.getProperty("users")+usNumber;
        return user;
    }

    public String getPost(int postNumber){
        String user = ReadProperties.getProperty("posts")+ postNumber;
        return user;
    }
}

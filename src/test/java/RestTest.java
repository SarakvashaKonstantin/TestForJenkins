import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Ordering;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@Test
public class RestTest {
    BaseRequest baseRequest = new BaseRequest();
    String usersPath = ReadProperties.getProperty("users");
    String postsPath = ReadProperties.getProperty("posts");
    String email5 = ReadProperties.getProperty("email");

    public void getPosts(){
        List<String> id = baseRequest.getMethod(postsPath,StatusCode.okCode.getCode())
                .extract().jsonPath().getList("id");
        Assert.assertEquals(Ordering.natural().isOrdered(id),true,"List order is incorrect");
    }

    public void getRealPost(){
        String res = baseRequest.getMethod(baseRequest.getPost(99),StatusCode.okCode.getCode())
                .body("userId",equalTo(10))
                .body("id",equalTo(99))
                .extract().jsonPath().getString("title");
        Assert.assertFalse (res == null,"Title is null");
        String res2 = baseRequest.getMethod(baseRequest.getPost(99),StatusCode.okCode.getCode())
                .extract().jsonPath().getString("body");
        Assert.assertFalse (res2 == null,"Body is null");
    }

    public void getUnrealPost(){
        baseRequest.getMethod(baseRequest.getPost(150),StatusCode.notFound.getCode())
                .body("[0]",equalTo(null));
    }

    public void addPost(){
        String someRandomString = String.format("%1$TH%1$TM%1$TS", new Date());
        String req1 = "{\n" +
                "  \"title\": \""+someRandomString+"\",\n" +
                "  \"body\": \""+someRandomString+"\",\n" +
                "  \"userId\": \"1\"";
        String req = req1 + "\n}";
        Response rs = baseRequest.postMethod(postsPath,req,StatusCode.created.getCode())
                .extract().response();
        String ans = rs.getBody().asString();
        Assert.assertEquals(ans,req1+",\n" +
                "  \"id\": 101\n}","Parameters don't match");
    }

    public void getUsers(){
        baseRequest.getMethod(usersPath,StatusCode.okCode.getCode());
        List<DeserRest> users = baseRequest.getMethod(usersPath,StatusCode.okCode.getCode())
                .extract().jsonPath().getList("",DeserRest.class);
        Assert.assertEquals(users.get(4).email,email5,"Parameters don't match");
    }

    public void getRealUser(){
        List<DeserRest> users = baseRequest.getMethod(usersPath,StatusCode.okCode.getCode())
                .extract().jsonPath().getList("",DeserRest.class);
        DeserRest rest = users.get(4);
    }

    public void getRealUser2() throws JsonProcessingException{
        String user = baseRequest.getMethod(baseRequest.getUser(5),StatusCode.okCode.getCode())
                .extract().response().asPrettyString();
        ObjectMapper mapper = new ObjectMapper();
        String users = given()
                .baseUri(ReadProperties.getProperty("host"))
                .basePath(baseRequest.getUser(5))
                .contentType(ContentType.JSON)
                .when().get().asPrettyString();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        List<DeserRest> deserRestList = mapper.readValue(users,
                new TypeReference<List<DeserRest>>() {});
        Assert.assertEquals(deserRestList.get(0).email,email5,"Parameters don't match");
        Assert.assertEquals(user,users,"Parameters don't match");
    }
}
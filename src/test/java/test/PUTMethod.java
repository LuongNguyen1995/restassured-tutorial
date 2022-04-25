package test;

import com.google.gson.Gson;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.BuildModelJSON;
import model.PostBody;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class PUTMethod {
    public static void main(String[] args) {

        String baseUri = "https://jsonplaceholder.typicode.com";

        //Form up request object anad header
        RequestSpecification request = given();
        request.baseUri(baseUri);

        request.header(new Header("Content-type", "application/json; charset=UTF-8"));

        //Contruct body
        PostBody postBody1 = new PostBody(1, 1, "New title 1",  "New body 1");
        PostBody postBody2= new PostBody(1, 1, "New title 2",  "New body 2");
        PostBody postBody3 = new PostBody(1, 1, "New title 3",  "New body 3");

        List<PostBody> postBodies = Arrays.asList(postBody1, postBody2, postBody3);

        for (PostBody postBody : postBodies) {
            System.out.println(postBody);
            String postBodyStr = BuildModelJSON.parseJSONString(postBody);


            //Send request
            int TARGET_POST_NUMBER =1 ;
            Response response =  request.body(postBodyStr).put("/posts/".concat(String.valueOf(TARGET_POST_NUMBER)));
//            response.prettyPrint();

            //Verification
            response.then().body("userId", equalTo(postBody.getUserId()));
            response.then().body("id", equalTo(postBody.getId()));
            response.then().body("title", equalTo(postBody.getTitle()));
            response.then().body("body", equalTo(postBody.getBody()));
        }

    }
}

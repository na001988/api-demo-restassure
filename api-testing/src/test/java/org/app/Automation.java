package org.app;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.module.Users;
import org.utils.Constants;
import org.utils.DataStore;
import java.io.File;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.equalTo;

public class Automation {

    Gson gson = new Gson();

    @Test
    public void create_resource(){

        RestAssured.baseURI = Constants.uri;
        RequestSpecification rsp = RestAssured.given();
        rsp.header(new Header("Content-type","application/json; charset=UTF-8"));
        Response resp;

        Gson gson = new Gson();
        Users model_users = new Users();

        model_users.setAddress("Some add");
        model_users.setBaseSalary(5000.00);
        model_users.setDepartment("IT");
        model_users.setDateOfBirth("01/01/2000");
        model_users.setEmail("some@mail.com");
        model_users.setFirstName("Name");
        model_users.setLastName("last-name");
        model_users.setMobile("145464546546");
        model_users.setJobTitle("Operator");
        model_users.setStartDate("01/01/2020");

        resp = rsp.body(gson.toJson(model_users)).post("/users");
        resp.prettyPrint();
        resp.then().statusCode(equalTo(Constants.res_code_created));

        JsonPath js = resp.jsonPath();
        String tmp  = js.get("_id");

        DataStore.setValue("id",tmp);
        String my_id = (String) DataStore.getValue("id");
        System.out.println("#########: "+my_id);

    }

    @Test
    public void get_resource(){

        String user1 = "63d5d33407307e03e8c76b71";

        RestAssured.baseURI = Constants.uri;
        RequestSpecification rsp = RestAssured.given();
        Response resp = rsp.request(Method.GET,"/users");

        ResponseBody body = resp.getBody();
        body.prettyPrint();

        Assertions.assertEquals(200,resp.getStatusCode());
        String user_name = resp.getBody().asString();
        //Assertions.assertTrue(user_name.contains("Name"));

    }

    @Test
    public void update_resource(){

        String user1 = "63d5c51707307e03e8c76b66";
        RestAssured.baseURI = Constants.uri;
        RequestSpecification rsp = RestAssured.given();
        rsp.header(new Header("Content-type","application/json; charset=UTF-8"));

        Gson gson = new Gson();
        Users model_users = new Users();
        model_users.setAddress("Some add");
        model_users.setBaseSalary(900.00);
        model_users.setDepartment("IT");
        model_users.setDateOfBirth("01/01/2000");
        model_users.setEmail("some@mail.com");
        model_users.setFirstName("Perfect-name");
        model_users.setLastName("last-name");
        model_users.setMobile("145464546546");
        model_users.setJobTitle("Operator");
        model_users.setStartDate("01/01/2020");

        Response resp = rsp.body(gson.toJson(model_users)).put("/users/"+user1);
        Assertions.assertEquals(200,resp.getStatusCode());

    }


    @Test
    public void delete_resource(){

        String user1 = "63d5c3ea07307e03e8c76b65";
        RestAssured.baseURI = Constants.uri;
        RequestSpecification rsp = RestAssured.given();
        Response resp = rsp.request(Method.DELETE,"/users/"+user1);
        Assertions.assertEquals(200,resp.getStatusCode());

    }

    @Test
    public void validaSchemaPost(){
        File configSchema = new File(
                System.getProperty("user.dir") + File.separator + "src/test/java/org/schemas/post.json");

        RestAssured.baseURI = Constants.uri;
        RequestSpecification rsp = RestAssured.given();
        rsp.header(new Header("Content-type","application/json; charset=UTF-8"));
        Response resp;

        Gson gson = new Gson();
        Users model_users = new Users();

        model_users.setAddress("Some add");
        model_users.setBaseSalary(5000.00);
        model_users.setDepartment("IT");
        model_users.setDateOfBirth("01/01/2000");
        model_users.setEmail("some@mail.com");
        model_users.setFirstName("Name");
        model_users.setLastName("last-name");
        model_users.setMobile("145464546546");
        model_users.setJobTitle("Operator");
        model_users.setStartDate("01/01/2020");

        resp = rsp.body(gson.toJson(model_users)).post("/users");
        resp.prettyPrint();
        resp.then().body(matchesJsonSchema(configSchema));


    }
}

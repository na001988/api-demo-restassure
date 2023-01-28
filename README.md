# api-demo-restassure

This repo is for demostration purposes, it is intented for testing an API by using restassured testing automation framework to automate the validation of endpoints.
The project was created using Intelij Community Edition

The test scenararios were created in the class DemoAutomation.


//08.08.2022

//remember to update:
DartifactId = ?


//start by set up project

mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DgroupId=com.apress.app -DartifactId=demo-app-001 -Dversion=1.0 -Dpackage=main

//finish

/*
this approach is to implement
passing parameters from one test to another
*/

public class DataStore {
    private DataStore() {};

    private static LinkedHashMap<String, Object> dataMap = new LinkedHashMap<String,Object>();

    public static void setValue(String key, Object value){
        dataMap.put(key,value);
    }

    public static Object getValue(String key){
        return dataMap.get(key);
    }

}


public class Users {

    static String _id;

    private String FirstName;
    private String LastName;
    private String DateOfBirth;
    private String StartDate;
    private String Department;
    private String JobTitle;
    private String Email;
    private String Mobile;
    private String Address ;
    private Double BaseSalary;

}

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

/*
Date" 28/07/2022
Tester: Roberto Zumba Naranjo
- Main class to execute the scenarios for CRUD operations
- [not implemented]: Schema Validation
* */

public class DemoAutomation {

    //from https://crudcrud.com/
    String uri = "https://crudcrud.com/api/e0599142178b42dba8e433a837d94d7b";

    @Test
    void Verify_API_Call() {
        //This test is implemented using Exceptions
        baseURI=uri;
        Response res = get(baseURI+"/Employees");
        if(res.getStatusCode()>400){
            throw new RuntimeException("Error while sending request on > Verify_API_Call()");
        }else{
            Assert.assertEquals(res.getStatusCode(), 200);
        }

    }

    @Test (priority = 3)
    void Verify_single_value_is_returned(){
        baseURI=uri;
        String my_id = (String) DataStore.getValue("my-id");
        Users._id =
        given().pathParam("_id",my_id)
                .given().header("Content-Language", "en_US")
                .contentType("application/json")
                .when()
                .get(baseURI+"/Employees/{_id}")
                .then()
                .statusCode(200)
                .log()
                .all()
                .extract()
                .jsonPath()
                .getString("_id");

    }

    @Test (priority = 1)
    void Verify_new_record_is_created(){
        baseURI=uri;
        Users obj = Users.builder()
                .FirstName("Robert")
                .LastName("Operator")
                .DateOfBirth("29/11/76")
                .StartDate("01/07/2022")
                .Department("IT")
                .JobTitle("Devops")
                .Email("rn@gmail.com")
                .Mobile("0406900000")
                .Address("Some place St")
                .BaseSalary(50000.00)
                .build();


        Users._id =
        given()
                .body(obj)
                .contentType(ContentType.JSON)
                .when()
                .post(baseURI+"/Employees")
                .then()
                .statusCode(201).
                log().
                all().
                extract()
                .jsonPath()
                .getString("_id");

        DataStore.setValue("my-id",Users._id );

    }

    @Test (priority = 2)
    void Verify_single_record_is_updated(){
        baseURI=uri;
        String my_id = (String) DataStore.getValue("my-id");
        Users obj = Users.builder()
                .FirstName("Robert2")
                .LastName("Operator2")
                .DateOfBirth("29/11/76")
                .StartDate("07/07/2022")
                .Department("IT2")
                .JobTitle("Devops2")
                .Email("rn@gmail.com")
                .Mobile("0406900000")
                .Address("Some place St")
                .BaseSalary(10000.00)
                .build();

        given()
                .pathParam("_id",my_id)
                .body(obj)
                .contentType(ContentType.JSON)
                .when()
                .put(baseURI+"/Employees/{_id}")
                .then()
                .statusCode(200);

    }

    @Test (enabled = false)
    void Verify_single_record_is_deleted(){
        baseURI=uri;
        String my_id = (String) DataStore.getValue("my-id");
        given()
                .pathParam("_id",my_id)
                .when()
                .delete(baseURI+"/Employees/{_id}")
                .then()
                .statusCode(200);
    }
}

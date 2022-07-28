import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

/*
Date" 28/07/2022
Tester: Roberto Zumba Naranjo
- Main class to execute the scenarios for CRUD operations
- Reports under: test-output/index.html
- Reports under: test-output/emailable-report.html
- [not implemented]: Schema Validation
* */

public class DemoAutomation {

    //from https://crudcrud.com/
    String uri = "https://crudcrud.com/api/5ee3c8b1b6bd4842aecf830d0ed51ad7";

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

    @Test (priority = 4)
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

package fr.ing.secu.leakybank.steps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import org.springframework.boot.context.embedded.LocalServerPort;

import io.cucumber.java8.En;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class LoginStepDefinitions implements En {

    @LocalServerPort
    private Integer localPort = 8080;
    
    private static Response response;
    private static boolean hasLogin;
    private static boolean hasPassword;
    private static boolean isUnknown;
    
    public LoginStepDefinitions() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/";
        
        Before(()-> {
            response = null;
            hasLogin = true;
            hasPassword = true;
            isUnknown = false;
        });

        Given("a user {word} a login and {word} a password", (String login, String password) ->{
            //System.out.println("Given ok");
            hasLogin = "with".equals(login);
            hasPassword = "with".equals(password);
            isUnknown = false;
        });

        
        Given("an unknown user", () ->{
            //System.out.println("Given ko");
            isUnknown = true;
        });

        When("the user try to log", () -> {
            //System.out.println("When ok");
            RequestSpecification request = given().port(localPort).request();
            if(isUnknown){
                request = request
                    .formParam("login", "test")
                    .formParam("password", "test");
            } else {
                if (hasLogin) request = request.formParam("login", "alice");
                if (hasPassword) request = request.formParam("password", "aliceP@ssw");
            }
            response = request.when().post("login");
        });

        Then("the response is {word}", (String status) -> {
            //System.out.println("Then ok");
            if("ok".equals(status)){
                response.then().statusCode(200);
            }else {
                response.then().statusCode(302);
            }
        });
        Then("the answer is {word} error", (String hasError) -> {
            //System.out.println("Then ok");
            if ("with".equals(hasError)){
                if (!hasLogin){
                    response.then().assertThat().body(containsString("Login is required"));
                } else if (!hasPassword){
                    response.then().assertThat().body(containsString("Password is required"));
                }else {
                    response.then().assertThat().body(containsString("Invalid credentials, please retry"));
                }
            } else {
                response.then().assertThat()
                    .body(not(containsString("Login is required")))
                    .body(not(containsString("Password is required")))
                    .body(not(containsString("Invalid credentials, please retry")));
            }   
        });
    }

}

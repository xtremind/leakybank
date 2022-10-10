package fr.ing.secu.leakybank.steps;

import io.cucumber.java8.En;
import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.context.embedded.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

public class TransactionsStepDefinitions implements En {
    @LocalServerPort
    private Integer localPort = 8080;
    private static Response response;
    private static boolean hasDebitAccount;
    private static boolean hasCreditAccount;
    private static boolean hasAmount;
    private static boolean hasSameAccount;
    private static RequestSpecification request;

    public TransactionsStepDefinitions() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/";

        Before(() -> {
            response = null;
            hasDebitAccount = false;
            hasCreditAccount = false;
            hasAmount = false;
            hasSameAccount = false;

            request = given().port(localPort).request().given().auth().form("alice", "aliceP@ssw",
                    new FormAuthConfig("/login", "login", "password"));
        });


        Given("a user {word} debitAccount and {word} creditAccount and {word} amount", (String debitAccount, String creditAccount, String amount) -> {
            hasDebitAccount = "with".equals(debitAccount);
            hasCreditAccount = "with".equals(creditAccount);
            hasAmount = "with".equals(amount);
        });

        When("the user try to transfer money from one account to another", () -> {
            if (hasDebitAccount) {
                request = request.formParam("debitAccount", 100123);
            } else {
                request = request.formParam("debitAccount", 0);
            }
            if (hasCreditAccount) {
                request = request.formParam("creditAccount", 100124);
            } else {
                request = request.formParam("creditAccount", 0);
            }
            if (hasAmount) {
                request = request.formParam("amount", 50);
            } else {
                request = request.formParam("debitAccount", 0);
            }
            response = request.when().post("transfers/new");
        });

        When("the user try to transfer money on same account", () -> {
            if (hasDebitAccount) request = request.formParam("debitAccount", 100123);
            if (hasCreditAccount) request = request.formParam("creditAccount", 100123);
            if (hasAmount) request = request.formParam("amount", 50);
            hasSameAccount = true;
            response = request.when().post("transfers/new");

        });

        Then("Money Transfer response is {word}", (String status) -> {
            if ("ok".equals(status)) {
                response.then().statusCode(200);
            } else {
                response.then().statusCode(302);
            }
        });

        Then("Money Transfer answer is {word} error", (String hasError) -> {
            if ("with".equals(hasError)) {
                if (!hasDebitAccount) {
                    response.then().assertThat().body(containsString("Please select a debited account."));
                }
                if (!hasCreditAccount) {
                    response.then().assertThat().body(containsString("Please select a credited account."));
                }
                if (!hasAmount) {
                    response.then().assertThat().body(containsString("Amount is required."));
                }
                if (hasSameAccount) {
                    response.then().assertThat().body(containsString("Please select distinct debit and credit accounts."));
                }
            } else {
                response.then().assertThat()
                        .body(not(containsString("Please select a debited account.")))
                        .body(not(containsString("Please select a credited account.")))
                        .body(not(containsString("Amount is required.")))
                        .body(not(containsString("Please select distinct debit and credit accounts.")));
            }
        });

    }

}

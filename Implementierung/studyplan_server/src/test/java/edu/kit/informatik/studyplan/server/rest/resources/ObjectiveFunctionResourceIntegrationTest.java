package edu.kit.informatik.studyplan.server.rest.resources;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static io.restassured.config.RestAssuredConfig.newConfig;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class ObjectiveFunctionResourceIntegrationTest extends SimpleRestAssuredTestWithAuth {
    private String accessToken;

    @Before
    public void setUp() throws Exception {
        String string = given().auth().basic("it_user", "password")
                .config(newConfig().redirect(redirectConfig().followRedirects(false)))
                .queryParam("client_id", "key-26hg02lsa")
                .queryParam("scope", "student")
                .queryParam("state", 42)
                .queryParam("response_type", "token")
                .get("/auth/login").getHeader("Location");
        int start = new URI(string).getFragment().indexOf("=") + 1;
        int end = new URI(string).getFragment().indexOf("&");
        accessToken = "Bearer " + new URI(string).getFragment().substring(start, end);

        //Set up student
        HashMap<String, Object> json = new HashMap<>();
        HashMap<String, Object> discipline = new HashMap<>();
        HashMap<String, Object> studyStart = new HashMap<>();
        HashMap<String, Object> student = new HashMap<>();
        student.put("discipline", discipline);
        student.put("study-start", studyStart);
        json.put("student", student);
        discipline.put("id", 1);
        studyStart.put("semester-type", "WT");
        studyStart.put("year", 2015);

        customAuthorize(given(), accessToken)
                .contentType("application/json")
                .body(json)
                .put("/student").then()
                .assertThat().statusCode(200);
    }

    @After
    public void tearDown() throws Exception {
        customAuthorize(given(), accessToken).delete("student").then()
                .assertThat().statusCode(200);
    }

    @Test
    public void getAllObjectiveFunctionsValidKeys() throws Exception {
        customAuthorize(given(), accessToken).get("objective-functions").then().assertThat().
                statusCode(200).
                body("functions.every { it.containsKey('id') && it.containsKey('name') }", is(true));
    }

    @Test
    public void getAllObjectiveFunctionsValidIds() throws Exception {
    	customAuthorize(given(), accessToken).get("objective-functions").then().assertThat().
                statusCode(200).
                body("functions.every { it.id >= 0 }", is(true));
    }

    @Test
    public void getAllObjectiveFunctionsHasElements() throws Exception {
    	customAuthorize(given(), accessToken).get("objective-functions").then().assertThat()
                .statusCode(200)
                .body("functions.size", greaterThan(0));
    }

}
package services;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.util.List;


public class PetApi {
  private static final String BASE_URL = "https://petstore.swagger.io";
  public static final String BASE_PATH = "/v2/pet";
  public static final String FIND_BY_STATUS = "/findByStatus";
  private RequestSpecification specification;

  public PetApi() {
    specification =
        given()
            .baseUri(BASE_URL)
            .basePath(BASE_PATH)
            .accept(ContentType.JSON)
            .log().all();
  }

  public ValidatableResponse getPetList(List<String> petStatus) {
    return given(specification)
        .queryParam("status", petStatus)
        .when()
        .get(FIND_BY_STATUS)
        .then()
        .log().all();
  }
}

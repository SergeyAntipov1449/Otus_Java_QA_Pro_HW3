package services;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.util.List;


public class PetApi {
  private final String baseUrl = System.getProperty("base.url");
  public final String basePath = "/v2/pet";
  public final String findByStatus = "/findByStatus";
  private RequestSpecification specification;

  public PetApi() {
    specification =
        given()
            .baseUri(baseUrl)
            .basePath(basePath)
            .accept(ContentType.JSON)
            .log().all();
  }

  public ValidatableResponse getPetList(List<String> petStatus) {
    return given(specification)
        .queryParam("status", petStatus)
        .when()
        .get(findByStatus)
        .then()
        .log().all();
  }
}

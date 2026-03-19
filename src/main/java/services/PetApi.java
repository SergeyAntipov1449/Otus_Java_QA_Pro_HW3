package services;

import static io.restassured.RestAssured.given;

import io.restassured.response.ValidatableResponse;
import java.util.List;


public class PetApi extends AbsApi {
  public final String basePath = "/v2/pet";
  public final String findByStatus = "/findByStatus";

  public PetApi() {
  }

  public ValidatableResponse getPetList(List<String> petStatus) {
    return given(super.specification)
        .basePath(basePath)
        .queryParam("status", petStatus)
        .when()
        .get(findByStatus)
        .then()
        .log().all();
  }
}

package services;

import static io.restassured.RestAssured.*;

import dto.user.IUserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;


public class UserApi {
  private static final String BASE_URL = "https://petstore.swagger.io";
  public static final String BASE_PATH = "/v2/user";
  private RequestSpecification specification;

  public UserApi() {
    specification =
        given()
            .baseUri(BASE_URL)
            .basePath(BASE_PATH)
            .accept(ContentType.JSON)
            .log().all();
  }

  public ValidatableResponse createUser(IUserDTO userDTO) {
    return given(specification)
        .contentType(ContentType.JSON)
        .body(userDTO)
        .when()
        .post()
        .then()
        .log().all();
  }
}

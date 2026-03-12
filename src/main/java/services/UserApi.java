package services;

import static io.restassured.RestAssured.*;

import dto.user.IUserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;


public class UserApi {
  private final String baseUrl = System.getProperty("base.url");
  public final String basePath = "/v2/user";
  private RequestSpecification specification;

  public UserApi() {
    specification =
        given()
            .baseUri(baseUrl)
            .basePath(basePath)
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

  public ValidatableResponse getUserByName(String userName) {
    return given(specification)
        .when()
        .get("/" + userName)
        .then()
        .log().all();
  }

  public ValidatableResponse deleteUserByName(String userName) {
    return given(specification)
        .when()
        .delete("/" + userName)
        .then()
        .log().all();
  }


}

package services;

import static io.restassured.RestAssured.*;

import dto.user.IUserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;


public class UserApi extends AbsApi {
  public final String basePath = "/v2/user";

  public UserApi() {
  }

  public ValidatableResponse createUser(IUserDTO userDTO) {
    return given(super.specification)
        .basePath(basePath)
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

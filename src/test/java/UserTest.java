import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import dto.user.CreateUserResponseDTO;
import dto.user.UserDTO;
import dto.user.WrongIdTypeUserDTO;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.UserApi;


public class UserTest {
  @Test
  @DisplayName("Успешное создание пользователя")
  //Проверка успешного создания пользователя. Запрос c валидными данным для всех полей.
  void createUser() {
    UserApi userApi = new UserApi();
    UserDTO allFieldsUserDTO = UserDTO.builder()
        .id(1313L)
        .userName("SergeyAnt")
        .firstName("Sergey")
        .lastName("Antipov")
        .email("sergey@mail.com")
        .password("PaSsWoRd")
        .userStatus(1L)
        .build();

    ValidatableResponse response = userApi.createUser(allFieldsUserDTO);
    CreateUserResponseDTO createdUser = response.extract().body().as(CreateUserResponseDTO.class);

    Assertions.assertAll(
        () -> response.statusCode(HttpStatus.SC_OK),
        () -> response.body(matchesJsonSchemaInClasspath("schema/CreateUserSchema.json")),
        () -> Assertions.assertEquals(createdUser.getCode(), 200, "Incorrect code"),
        () -> Assertions.assertEquals(createdUser.getType(), "unknown", "Incorrect type"),
        () -> Assertions.assertEquals(createdUser.getMessage(), allFieldsUserDTO.getId().toString(), "Incorrect message")
    );
  }

  @Test
  @DisplayName("Ошибка при создании пользователя с невалидным id")
  //Проверка получания ошибки при создании пользователя с невалидным id. String вместо long.
  void failCreateUser() {
    UserApi userApi = new UserApi();
    WrongIdTypeUserDTO wrongIdTypeUserDTO = WrongIdTypeUserDTO.builder()
        .id("one")
        .userName("SergeyAnt")
        .firstName("Sergey")
        .lastName("Antipov")
        .email("sergey@mail.com")
        .password("PaSsWoRd")
        .userStatus(1L)
        .build();

    ValidatableResponse response = userApi.createUser(wrongIdTypeUserDTO);
    CreateUserResponseDTO createdUser = response.extract().body().as(CreateUserResponseDTO.class);

    Assertions.assertAll(
        () -> response.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR),
        () -> response.body(matchesJsonSchemaInClasspath("schema/CreateUserSchema.json")),
        () -> Assertions.assertEquals(createdUser.getCode(), 500, "Incorrect code"),
        () -> Assertions.assertEquals(createdUser.getType(), "unknown", "Incorrect type"),
        () -> Assertions.assertEquals(createdUser.getMessage(), "something bad happened", "Incorrect message")
    );
  }

}

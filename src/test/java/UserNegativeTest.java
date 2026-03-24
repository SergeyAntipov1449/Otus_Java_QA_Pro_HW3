import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.google.inject.Inject;
import dto.user.CreateUserResponseDTO;
import dto.user.WrongIdTypeUserDTO;
import extensions.ApiExtensions;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import services.UserApi;



@ExtendWith(ApiExtensions.class)
public class UserNegativeTest {
  @Inject
  private UserApi userApi;
  @Test
  @DisplayName("Ошибка при создании пользователя с невалидным id")
  //Проверка получания ошибки при создании пользователя с невалидным id. String вместо long.
  void failCreateUser() {
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

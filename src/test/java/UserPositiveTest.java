import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.google.inject.Inject;
import dto.user.CreateUserResponseDTO;
import dto.user.UserDTO;
import extensions.ApiExtensions;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import services.UserApi;


@ExtendWith(ApiExtensions.class)
public class UserPositiveTest {
  @Inject
  private UserApi userApi;
  @Test
  @DisplayName("Успешное создание пользователя")
  //Проверка успешного создания пользователя. Запрос c валидными данным для всех полей.
  void createUser() {
    UserDTO allFieldsUserDTO = UserDTO.builder()
        .id(1313L)
        .userName("SergeyAnt")
        .firstName("Sergey")
        .lastName("Antipov")
        .email("sergey@mail.com")
        .password("PaSsWoRd")
        .userStatus(1L)
        .build();

    ValidatableResponse createResponse = userApi.createUser(allFieldsUserDTO);
    CreateUserResponseDTO createdUser = createResponse.extract().body().as(CreateUserResponseDTO.class);

    //ValidatableResponse getResponse = userApi.getUserByName(allFieldsUserDTO.getUserName()); //запрос созданого пользователя
    //UserDTO receivedUser = getResponse.extract().body().as(UserDTO.class); //DTO созданного поьзователя

    Assertions.assertAll(
        () -> createResponse.statusCode(HttpStatus.SC_OK),
        () -> createResponse.body(matchesJsonSchemaInClasspath("schema/CreateUserSchema.json")),
        () -> Assertions.assertEquals(createdUser.getCode(), 200, "Incorrect code"),
        () -> Assertions.assertEquals(createdUser.getType(), "unknown", "Incorrect type"),
        () -> Assertions.assertEquals(createdUser.getMessage(), allFieldsUserDTO.getId().toString(), "Incorrect message")
    //        () -> getResponse.statusCode(HttpStatus.SC_OK), //проверяем успешный ответ при получении пользователя
    //        () -> Assertions.assertEquals(allFieldsUserDTO, receivedUser) //проверяем соответствие DTO отправленного и полученного пользователя
    );
    // ValidatableResponse deleteResponse = userApi.deleteUserByName(allFieldsUserDTO.getUserName()); //запрос удаления созданного пользователя
    // deleteResponse.statusCode(HttpStatus.SC_NO_CONTENT); //проверяем успешный ответ при удалении пользовтеля

  }
}

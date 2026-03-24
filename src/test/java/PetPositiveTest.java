import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.google.inject.Inject;
import dto.pet.PetInfo;
import extensions.ApiExtensions;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import services.PetApi;
import java.util.Arrays;
import java.util.List;

@ExtendWith(ApiExtensions.class)
public class PetPositiveTest {
  @Inject
  private PetApi petApi;

  @Test
  @DisplayName("Список животных с выбранным статусом")
  //Проверка, что в ответе присутствуют животные с выбранными статусами
  void statusCheck() {
    List<String> statuses = Arrays.asList("available", "sold");
    ValidatableResponse response = petApi.getPetList(statuses);

    List<PetInfo> pets = response.extract().as(new io.restassured.common.mapper.TypeRef<List<PetInfo>>() {
    });

    Assertions.assertAll(
        () -> response.statusCode(HttpStatus.SC_OK),
        () -> response.body(matchesJsonSchemaInClasspath("schema/FindPetByStatusSchema.json")),
        () -> Assertions.assertTrue(this.isStatusCorrect(pets, statuses), "Incorrect status")
    );
  }

  @Test
  @DisplayName("Успешный запрос с невалидным статусом")
  //Проверка успешного запроса с невалидным статусом
  void invalidStatus() {
    List<String> statuses = Arrays.asList("ok", "waiting", "absent");
    ValidatableResponse response = petApi.getPetList(statuses);

    List<PetInfo> pets = response.extract().as(new io.restassured.common.mapper.TypeRef<List<PetInfo>>() {
    });

    Assertions.assertAll(
        () -> response.statusCode(HttpStatus.SC_OK),
        () -> response.body(matchesJsonSchemaInClasspath("schema/FindPetByStatusSchema.json")),
        () -> Assertions.assertTrue(pets.isEmpty())
    );
  }

  private boolean isStatusCorrect(List<PetInfo> petInfo, List<String> statuses) {
    for (PetInfo pet : petInfo) {
      if (!statuses.contains(pet.getStatus())) return false;
    }
    return true;
  }
}

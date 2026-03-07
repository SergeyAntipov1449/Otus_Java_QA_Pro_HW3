import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import dto.pet.PetInfo;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.PetApi;
import java.util.Arrays;
import java.util.List;

public class PetTest {
  @Test
  @DisplayName("Список животных с выбранным статусом")
  //Проверка, что в ответе присутствуют животные с выбранными статусами
  void statusCheck() {
    PetApi petApi = new PetApi();
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
  @DisplayName("Ошибка при запросе с невалидным статусом")
  //Проверка успешного запроса с невалидным статусо
  void invalidStatus() {
    PetApi petApi = new PetApi();
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

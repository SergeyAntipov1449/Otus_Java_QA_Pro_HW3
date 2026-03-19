package services;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public abstract class AbsApi {
  protected final String baseUrl = System.getProperty("base.url");
  protected RequestSpecification specification;

  public AbsApi() {
    specification =
        given()
            .baseUri(baseUrl)
            .accept(ContentType.JSON)
            .log().all();
  }
}

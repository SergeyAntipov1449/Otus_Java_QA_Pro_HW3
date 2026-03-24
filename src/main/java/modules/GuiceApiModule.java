package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import services.PetApi;
import services.UserApi;

public class GuiceApiModule extends AbstractModule {

  @Provides
  public PetApi getPetApi() {
    return new PetApi();
  }

  @Provides
  public UserApi getUserApi() {
    return new UserApi();
  }
}

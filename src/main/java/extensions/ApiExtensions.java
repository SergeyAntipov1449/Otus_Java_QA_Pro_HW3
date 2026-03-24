package extensions;

import com.google.inject.Guice;
import modules.GuiceApiModule;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ApiExtensions implements BeforeEachCallback {
  @Override
  public void beforeEach(ExtensionContext context) {
    Guice.createInjector(new GuiceApiModule()).injectMembers(context.getTestInstance().get());
  }
}

package springing.struts2.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springing.struts2.configuration.entities.StrutsConfig;

@Configuration
public class Struts2Configuration {
  public Struts2Configuration(
    RequestMappingHandlerMapping mapping
  ) {
    this.mapping = mapping;
    registerRequestHandlers();
  }

  private final RequestMappingHandlerMapping mapping;
  private void registerRequestHandlers() {
    System.out.println("=====================================================");
    getStrutsConfig().registerRequestHandlers(this.mapping);
  }

  /*
  @Override
  protected void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
    System.out.println("=====================================================");
    getStrutsConfig().registerExceptionHandlers(exceptionResolvers);
  }
  */

  public StrutsConfig getStrutsConfig() {
    if (config != null) return config;
    config = StrutsConfig.parseConfigFileAt("/struts.xml");
    return config;
  }

  private @Nullable StrutsConfig config;
}

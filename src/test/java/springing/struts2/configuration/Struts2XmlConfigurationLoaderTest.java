package springing.struts2.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springing.struts2.configuration.entities.StrutsConfig;

@ExtendWith(SpringExtension.class)
public class Struts2XmlConfigurationLoaderTest {

  public Struts2XmlConfigurationLoaderTest(
    GenericApplicationContext context
  ) {
    this.context = context;
    context.registerBean(RequestMappingHandlerMapping.class, RequestMappingHandlerMapping::new);
  }
  private final GenericApplicationContext context;

  @Test
  public void testParse() {
    var config = StrutsConfig.parseConfigFileAt("/hello-world/struts.xml");
  }
}

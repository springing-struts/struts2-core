package springing.struts2.configuration.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StrutsConfigTest {
  @Test
  public void testParse() {
    var config = StrutsConfig.parseConfigFileAt("/hello-world/struts.xml");
    assertNotNull(config);
  }
}

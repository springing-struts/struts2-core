package springing.struts2.configuration.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class DefaultActionRef {
  @JsonCreator
  public DefaultActionRef(
    @JacksonXmlProperty(localName = "name") String name
  ) {
    this.name = name;
  }
  private final String name;

  @JsonBackReference
  private PackageConfig packageConfig;

  public ActionConfig resolve() {
    var config = packageConfig.findActionConfigByName(name);
    if (config == null) throw new RuntimeException(
      "Unknown action name: " + name
    );
    return config;
  }
}

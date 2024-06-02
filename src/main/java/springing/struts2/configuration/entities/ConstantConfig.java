package springing.struts2.configuration.entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ConstantConfig {
  @JacksonXmlProperty(localName = "name")
  private String name;
  @JacksonXmlProperty(localName = "value")
  private String value;
}

package springing.struts2.configuration.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ActionInterceptorRef {
  @JsonCreator
  public ActionInterceptorRef(
    @JacksonXmlProperty(localName = "name",  isAttribute = true) String name
  ) {
    this.name = name;
  }
  private String name;
}

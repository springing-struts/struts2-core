package springing.struts2.configuration.entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import com.opensymphony.xwork2.Action;

public class ActionResultConfig {

  @JacksonXmlProperty(localName = "name", isAttribute = true)
  private String name = Action.SUCCESS;
  public String getName() {
    return name;
  }

  @JacksonXmlText
  private String path;
  public String getPath() {
    return path;
  }

  @JacksonXmlProperty(localName = "type", isAttribute = true)
  private String type;
}

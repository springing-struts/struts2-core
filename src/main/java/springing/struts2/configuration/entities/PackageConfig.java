package springing.struts2.configuration.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springing.struts2.action.DefaultActionResolver;

import java.util.List;

public class PackageConfig {
  @JacksonXmlProperty(localName = "name", isAttribute = true)
  private String name;
  public String getName() {
    return name;
  }

  @JsonBackReference
  private StrutsConfig strutsConfig;

  @JacksonXmlProperty(localName = "namespace", isAttribute = true)
  private String namespace;
  public String getNamespace() {
    return namespace;
  }

  @JacksonXmlProperty(localName = "extends", isAttribute = true)
  private String extendsPackage;

  @JacksonXmlProperty(localName = "default-action-ref")
  @JsonManagedReference
  @Nullable
  private DefaultActionRef defaultActionRef;

  @Nullable
  public DefaultActionRef getDefaultActionRef() {
    return defaultActionRef;
  }

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "action")
  @JsonManagedReference
  private List<ActionConfig> actions;

  @Nullable
  public ActionConfig findActionConfigByName(String name) {
    return actions.stream().filter(
        it -> name.equals(it.getName())
    ).findFirst().orElse(null);
  }

  public void registerRequestHandlers(RequestMappingHandlerMapping mapping) {
    actions.forEach(it -> it.registerRequestHandler(mapping));
  }

  public void registerExceptionHandlers(List<HandlerExceptionResolver> exceptionResolvers) {
    if (defaultActionRef != null) {
      var resolver = new DefaultActionResolver(defaultActionRef);
      exceptionResolvers.add(resolver);
    }
  }
}

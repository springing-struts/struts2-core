package springing.struts2.configuration.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.List;

@JacksonXmlRootElement(localName = "struts")
public class StrutsConfig {
  private static final XmlMapper xmlMapper = new XmlMapper();

  @JacksonXmlProperty(localName = "package")
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonManagedReference
  private List<PackageConfig> packages;

  @JacksonXmlProperty(localName = "constant")
  @JacksonXmlElementWrapper(useWrapping = false)
  private List<ConstantConfig> constants;

  public void registerRequestHandlers(RequestMappingHandlerMapping mapping) {
    this.packages.forEach(packageConfig -> {
      packageConfig.registerRequestHandlers(mapping);
    });
  }

  public void registerExceptionHandlers(List<HandlerExceptionResolver> exceptionResolvers) {
    this.packages.forEach(packageConfig -> {
      packageConfig.registerExceptionHandlers(exceptionResolvers);
    });
  }

  public static StrutsConfig parse(String xml) {
    try {
      return xmlMapper.readValue(xml, StrutsConfig.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static StrutsConfig parseConfigFileAt(String resourceClassPath) {
    var resource = new ClassPathResource(resourceClassPath);
    try (var in = resource.getInputStream()) {
      return xmlMapper.readValue(in, StrutsConfig.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

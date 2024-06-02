package springing.struts2.configuration.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springing.struts2.action.ActionController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ActionConfig {

  @JsonCreator
  public ActionConfig(
      @JacksonXmlProperty(localName = "name", isAttribute = true)
      String name
  ) {
    this.name = name;
  }
  private final String name;
  public String getName() {
    return this.name;
  }

  @JsonBackReference
  private @Nullable PackageConfig packageConfig;
  public PackageConfig getPackageConfig() {
    assert packageConfig != null;
    return packageConfig;
  }

  @JacksonXmlProperty(localName = "class", isAttribute = true)
  private @Nullable String actionClass;

  @JacksonXmlProperty(localName = "method", isAttribute = true)
  private @Nullable String methodName;

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "result")
  private List<ActionResultConfig> results = new ArrayList<>();

  @Nullable
  public ActionResultConfig getResultFor(String resultName) {
    var result = results.stream().filter(it -> resultName.equals(it.getName())).findFirst();
    return result.orElse(null);
  }

  @JacksonXmlProperty(localName = "interceptor-ref")
  @JacksonXmlElementWrapper(useWrapping = false)
  private List<ActionInterceptorRef> interceptorRefs = new ArrayList<>();

  private static final Method defaultMethod;
  static {
    try {
      defaultMethod = Controller.class.getDeclaredMethod(
       "handleRequest", HttpServletRequest.class, HttpServletResponse.class
      );
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  public void registerRequestHandler(RequestMappingHandlerMapping mappings) {
    mappings.registerMapping(
      RequestMappingInfo
        .paths("/" + name)
        .methods(RequestMethod.GET)
        .build(),
        new ActionController(this),
        defaultMethod
    );
  }

  /*
  public String executeAction() throws Exception {
    var actionResultName = getAction().execute();
    if (actionResultName == null) return null;
    return getResultFor(actionResultName).getPath();
  }
  */

  public Action getAction() {
    if (action == null) {
      action = createAction();
    }
    return action;
  }

  private @Nullable Action action;

  private Action createAction() {
    if (actionClass == null) {
      return new ActionSupport();
    }
    try {
      var clazz = Class.forName(actionClass);
      return (Action) clazz.getConstructor().newInstance();
    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException  e) {
      throw new RuntimeException(e);
    }
  }
}


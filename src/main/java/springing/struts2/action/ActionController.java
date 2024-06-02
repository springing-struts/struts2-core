package springing.struts2.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.PageContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import springing.struts2.configuration.entities.ActionConfig;

public class ActionController implements Controller {
  public ActionController(ActionConfig config) {
    this.config = config;
  }

  private final ActionConfig config;

  @Override
  public ModelAndView handleRequest(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws Exception {
    var action = config.getAction();
    var resultName = action.execute();
    if (resultName == null) return null;
    var result = config.getResultFor(resultName);
    if (result == null) return null;
    var view = result.getPath();
    var modelAndView = new ModelAndView(view);
    modelAndView.addObject("action", action);
    modelAndView.addObject("actionConfig", config);
    return modelAndView;
  }

  public static ActionConfig currentConfig() {
    var mayConfig = RequestContextHolder.currentRequestAttributes().getAttribute(
      "actionConfig", RequestAttributes.SCOPE_REQUEST
    );
    if (mayConfig instanceof ActionConfig config) {
      return config;
    }
    throw new RuntimeException(
      "An action config instance was not found in the PageContext."
    );
  }
}

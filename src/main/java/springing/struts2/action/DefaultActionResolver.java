package springing.struts2.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import springing.struts2.configuration.entities.ActionConfig;
import springing.struts2.configuration.entities.DefaultActionRef;

public class DefaultActionResolver implements HandlerExceptionResolver, Ordered {

  public DefaultActionResolver(
    DefaultActionRef defaultActionRef
  ) {
    this.actionConfig = defaultActionRef.resolve();
  }

  private final ActionConfig actionConfig;

  @Nullable
  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception e) {
    if (e instanceof NoHandlerFoundException || e instanceof NoResourceFoundException) {
      try {
        var action = actionConfig.getAction();
        var resultName = action.execute();
        if (resultName == null) return null;
        var result = actionConfig.getResultFor(resultName);
        if (result == null) return null;
        var viewPath = result.getPath();
        var modelAndPath = new ModelAndView(viewPath);
        modelAndPath.addObject("action", action);
        return modelAndPath;

      } catch (Exception actionError) {
        throw new RuntimeException(actionError);
      }
    }
    return null;
  }

  @Override
  public int getOrder() {
    return HIGHEST_PRECEDENCE;
  }
}

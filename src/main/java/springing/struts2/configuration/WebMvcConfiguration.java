package springing.struts2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import springing.struts2.configuration.entities.StrutsConfig;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  @Bean
  public ViewResolver jspViewResolver() {
    var bean = new InternalResourceViewResolver();
    bean.setViewClass(JstlView.class);
//    bean.setPrefix("/webapp/");
//    bean.setSuffix(".jsp");
    return bean;
  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.viewResolver(jspViewResolver());
  }



}

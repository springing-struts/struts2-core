package springing.struts2.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({WebMvcConfiguration.class, Struts2Configuration.class})
public class Struts2AutoConfiguration {
}

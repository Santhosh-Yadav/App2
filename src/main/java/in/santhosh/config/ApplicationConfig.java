package in.santhosh.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
//@Import({ DBconfiguration.class, ListenerConfig.class, SwaggerConfig.class })
@PropertySource({ "classpath:application.properties","classpath:log4j.properties","classpath:database.properties"})
public class ApplicationConfig {

}

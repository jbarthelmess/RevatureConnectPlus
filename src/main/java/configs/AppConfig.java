package configs;

import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class AppConfig {
    @Bean(name="allCors")
    public Javalin getJavalinAllCors() {
        return Javalin.create(JavalinConfig::enableCorsForAllOrigins);
    }

}

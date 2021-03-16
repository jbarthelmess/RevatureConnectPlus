package app;

import configs.AppConfig;
import io.javalin.Javalin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    Javalin app = ac.getBean("allCors", Javalin.class);
}

package prefrest.com.prod.cors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import prefrest.com.prod.config.property.ControleProperty;

@Configuration
public class CorsGlobal {
    @Autowired
    ControleProperty controleProperty;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(controleProperty.getServidor().getServidor(),
                                controleProperty.getServidor().getExterno(),
                                "http://localhost:4200",
                                "http://192.168.88.242:4200")
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
                        .allowedHeaders("Authorization", "Content-Type", "Accept")
                        .maxAge(3600);
            }
        };
    }
}
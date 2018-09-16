package prefrest.com.prod.cors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import prefrest.com.prod.config.property.ControleProperty;

/*@Component
@Order(Ordered.HIGHEST_PRECEDENCE)*/
public class CorsGlobal {
    @Autowired
    ControleProperty controleProperty;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
                        .allowedHeaders("Authorization", "Content-Type", "Accept", " X-PINGOTHER", "X-Requested-With", "Origin")
                        .maxAge(3600);
            }
        };
    }
}
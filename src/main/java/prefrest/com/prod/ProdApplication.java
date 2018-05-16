package prefrest.com.prod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import prefrest.com.prod.config.property.ControleProperty;

@SpringBootApplication
@EnableConfigurationProperties(ControleProperty.class)
public class ProdApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProdApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ProdApplication.class, args);
	}
}

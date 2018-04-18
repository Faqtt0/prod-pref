package prefrest.com.prod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import prefrest.com.prod.config.property.ControleProperty;

@SpringBootApplication
@EnableConfigurationProperties(ControleProperty.class)
public class ProdApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdApplication.class, args);
	}
}

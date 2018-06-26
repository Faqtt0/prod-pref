package prefrest.com.prod.config.property;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mproperties")
@Data
public class ControleProperty {
    private final Seguranca seguranca = new Seguranca();
    private final Servidor servidor = new Servidor();
    private final Cookie cookie = new Cookie();

    @Getter
    @Setter
    public static class Seguranca {
        private boolean enableHttps;
    }

    @Getter
    @Setter
    public static class Servidor{
        private String servidor;
        private String externo;
    }

    @Getter
    @Setter
    public static class Cookie{
        private int tempoVida = 0;
    }
}


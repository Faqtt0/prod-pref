package prefrest.com.prod.model;

import lombok.Data;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Data
public class Imagem {

    private Long id;
    @NotNull
    private String caminho;
    @Transient
    private String imagemBase64;
    //TODO ajustar imagem
}

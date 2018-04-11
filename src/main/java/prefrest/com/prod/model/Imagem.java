package prefrest.com.prod.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Imagem {

    private Long id;

    @NotNull
    private String caminho;

    @Transient
    private String imagemBase64;

    public Imagem(Long id, String caminho, String imagemBase64) {
        this.id = id;
        this.caminho = caminho;
        this.imagemBase64 = imagemBase64;
    }
}

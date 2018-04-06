package prefrest.com.prod.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Folder {

    private Long id;

    @NotNull
    @Size(min = 3, max = 200)
    private Long descricao;

    @NotNull
    private Integer ordem;

    private Long codImagem;
    //TODO Converter classe Imagens para Folder
}

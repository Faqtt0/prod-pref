package prefrest.com.prod.model.enquetes;

import lombok.Getter;

@Getter
public enum TipoPergunta {
    MULTI ("Multipla Escolha"),
    SINGLE ("Única Escolha");

    private String descricao;

    TipoPergunta(String descricao) {
        this.descricao = descricao;
    }

}
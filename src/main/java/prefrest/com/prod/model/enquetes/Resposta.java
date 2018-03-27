package prefrest.com.prod.model.enquetes;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Resposta {
    private Long codigo;
    @NotNull(message = "É necessário informar a pergunta a qual a resposta pertence")
    private Long codPergunta;
    @NotNull(message = "É necessário informar a enquete para a resposta")
    private Long codEnquete;

    @NotNull(message = "Não é possível incluir respostas vazias")
    @Size(min = 3, max = 1000)
    private String resposta;
}

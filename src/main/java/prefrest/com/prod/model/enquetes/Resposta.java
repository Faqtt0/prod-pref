package prefrest.com.prod.model.enquetes;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class Resposta {
    private Long codigo;
    @NotNull(message = "É necessário informar a pergunta a qual a resposta pertence")
    private Long codPergunta;
    @NotNull(message = "É necessário informar a enquete para a resposta")
    private Long codEnquete;

    @NotNull(message = "Não é possível incluir respostas vazias")
    @Size(min = 3, max = 1000)
    private String resposta;

    @Column(name = "ULTALT")
    private LocalDateTime ultAlt;
}

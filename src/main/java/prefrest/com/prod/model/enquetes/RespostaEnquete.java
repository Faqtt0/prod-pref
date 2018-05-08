package prefrest.com.prod.model.enquetes;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "RESPOSTAENQUETE")
@Data
public class RespostaEnquete {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 11, max = 11)
    private String cpf;

    @NotNull
    @Column(name = "IDENQUETE")
    private Long idEnquete;

    @NotNull
    @Column(name = "IDPERGUNTA")
    private Long idPergunta;

    @NotNull
    @Column(name = "IDRESPOSTA")
    private Long idResposta;
}

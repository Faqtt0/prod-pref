package prefrest.com.prod.model.enquetes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "ENQUETE")
public class Enquete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 15, max = 150)
    @NotNull
    private String descricao;

    private boolean ativo;

    @Column(name = "DATAINI")
    private LocalDate dataIni;
    @Column(name = "DATAFIM")
    private LocalDate dataFim;

    @Transient
    private List<Pergunta> perguntas;
}

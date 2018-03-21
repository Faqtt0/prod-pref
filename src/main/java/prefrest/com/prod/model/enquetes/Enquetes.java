package prefrest.com.prod.model.enquetes;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "ENQUETE")
public class Enquetes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 15, max = 150)
    private String descricao;

    private boolean ativo;

    @Column(name = "DATAINI")
    private LocalDate dataIni;
    @Column(name = "DATAFIM")
    private LocalDate dataFim;
}

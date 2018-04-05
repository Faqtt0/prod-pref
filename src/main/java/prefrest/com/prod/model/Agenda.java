package prefrest.com.prod.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "AGENDA")
@Data
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 200)
    private String descricao;

    @NotNull
    @Column(name = "DATAINI")
    private LocalDate dataIni;

    @NotNull
    @Column(name = "DATAFIM")
    private LocalDate dataFim;

    private String imagem;

    @Transient
    private String ImagemBase64;

    @JsonIgnore
    @Column(name = "ULTALT")
    private LocalDateTime ultAlt;

    @NotNull
    private boolean deletarAutomaticamente;
}

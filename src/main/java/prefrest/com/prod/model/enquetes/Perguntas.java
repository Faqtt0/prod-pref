package prefrest.com.prod.model.enquetes;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PERGUNTAS")
@Data
@IdClass(PerguntasID.class)
public class Perguntas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Id
    @ManyToOne
    @JoinColumn(name = "CODENQUETE")
    private Enquetes enquetes;

    @NotNull
    @Size(min = 15, max = 1000)
    private String pergunta;

    @Enumerated(value = EnumType.STRING)
    TipoPergunta tipoPergunta;
}
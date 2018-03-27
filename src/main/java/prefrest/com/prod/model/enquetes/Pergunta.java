package prefrest.com.prod.model.enquetes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Entity
@Table(name = "PERGUNTAS")
@Data
@IdClass(PerguntaID.class)
public class Pergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Id
    @ManyToOne
    @JoinColumn(name = "CODENQUETE")
    private Enquete enquete;

    @NotNull
    @Size(min = 15, max = 1000)
    private String pergunta;

    @Enumerated(value = EnumType.STRING)
    private TipoPergunta tipoPergunta;

    @Transient
    private List<Resposta> respostas;


}
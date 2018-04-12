package prefrest.com.prod.model.enquetes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PERGUNTA")
@Data
@IdClass(PerguntaID.class)
public class Pergunta {
    @Id
    private Long codigo;

    @Id
    @NotNull
    @Column(name = "CODENQUETE")
    private Long codEnquete;

    @NotNull
    @Size(min = 15, max = 1000)
    private String pergunta;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "TIPOPERGUNTA")
    private TipoPergunta tipoPergunta;

    @Transient
    private List<Resposta> respostas;

    @Column(name = "ULTALT")
    private LocalDateTime ultAlt;

}
package prefrest.com.prod.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "DELETETABLES")
@Data
@NoArgsConstructor
public class DeleteTables {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String tabela;

    @NotNull
    @Column(name = "IDTABELA")
    private Long idTabela;

    @NotNull
    @Column(name = "ULTALT")
    private LocalDateTime ultAlt;

    public DeleteTables(String tabela, Long idTabela, LocalDateTime ultAlt) {
        this.tabela = tabela;
        this.idTabela = idTabela;
        this.ultAlt = ultAlt;
    }
}

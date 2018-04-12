package prefrest.com.prod.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import prefrest.com.prod.repository.impl.CommomRepositoryImpl;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

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
    private String idTabela;

    @NotNull
    @Column(name = "ULTALT")
    private LocalDateTime ultAlt;

    public DeleteTables(Class<?> tabela, LocalDateTime ultAlt, Map<String, String> params) {
        if (CommomRepositoryImpl.isEntity(tabela)){
            Table table = CommomRepositoryImpl.getTable(tabela);
            this.tabela =  table.name();
        } else {
            this.tabela = tabela.getSimpleName();
        }
        StringBuilder sbParams =  new StringBuilder();
        params.forEach((s, s2) -> sbParams.append(s).append("=").append(s2).append(";"));

        this.idTabela = params.toString().replace("{", "").replace("}", ";");
        this.ultAlt = ultAlt;
    }
}

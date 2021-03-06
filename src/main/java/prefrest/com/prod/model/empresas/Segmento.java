package prefrest.com.prod.model.empresas;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "SEGMENTO")
@NoArgsConstructor
public class Segmento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Size(min = 3, max = 150)
    private String descricao;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Integer importancia;

    @Transient
    List<Empresa> empresas;

    @Column(name = "ULTALT")
    private LocalDateTime ultAlt;
}

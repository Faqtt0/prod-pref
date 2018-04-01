package prefrest.com.prod.model.empresas;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@Entity
@Table(name = "SEGMENTO")
public class Segmento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Size(min = 3, max = 150)
    private String descricao;

    @Transient
    List<Empresa> empresas;
}

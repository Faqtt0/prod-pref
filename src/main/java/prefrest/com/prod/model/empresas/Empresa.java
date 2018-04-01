package prefrest.com.prod.model.empresas;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "EMPRESA")
@Data
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;

    @NotNull
    @Column(name = "CODEMPRESA")
    private Long codEmpresa;

    @NotNull
    @Size(min = 3, max = 150)
    private String descricao;

    @Size(min = 10, max = 200)
    private String endereco;

    @Size(min = 5, max=10)
    private String bairro;

    private Integer numero;

    @NotNull
    private Integer telefone;

    @Size(min = 10, max = 150)
    private String email;

}

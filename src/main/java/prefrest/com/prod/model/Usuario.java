package prefrest.com.prod.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "USUARIOS")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 200)
    private String usuario;

    @Size(min = 3, max = 300)
    private String nome;

    @NotNull
    @Size(min = 1, max = 500)
    private String senha;
}

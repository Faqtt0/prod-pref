package prefrest.com.prod.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "FOLDER")
@Data
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 200)
    private Long descricao;

    @NotNull
    private Integer ordem;

    @Transient
    @Column(name = "CODIMAGEM")
    private Long codImagem;

    @Transient
    @Column(name = "ULTALT")
    private LocalDateTime ultAlt;

    @Transient
    private Imagem imagem;

}

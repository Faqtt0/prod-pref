package prefrest.com.prod.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "FOLDERHISTORICO")
@Data
public class FolderHistorico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 200)
    private String descricao;

    @JsonIgnore
    @Column(name = "CODIMAGEM")
    private Long codImagem;

    @Column(name = "ULTALT")
    private LocalDateTime ultAlt;

    @Transient
    private Imagem imagem;

}

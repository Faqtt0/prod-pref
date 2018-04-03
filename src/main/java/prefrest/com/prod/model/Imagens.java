package prefrest.com.prod.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "IMAGENS")
@Data
public class Imagens {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5, max = 200)
    private String descricao;

    @JsonIgnore
    private String imagem;

    @NotNull
    private Integer ordem;

    @JsonIgnore
    @Column(name = "ULTALT")
    private LocalDateTime ultAlt;

}
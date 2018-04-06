package prefrest.com.prod.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Agenda {
    private Long id;

    @NotNull
    @Size(min = 3, max = 200)
    private String descricao;

    @NotNull
    private LocalDate dataIni;

    @NotNull
    private LocalDate dataFim;

    private Long codImagem;

    @Transient
    private String ImagemBase64;

    @JsonIgnore
    private LocalDateTime ultAlt;

    @NotNull
    private boolean deletarAutomaticamente;

    //TODO criar configurações para agenda
}

package prefrest.com.prod.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Agenda {
    private Long id;
    private String descricao;
    private LocalDate dataIni;
    private LocalDate dataFim;
    private String imagem;
    private String ImagemBase64;
    private LocalDateTime ultAlt;
    private boolean deletarAutomaticamente;
}

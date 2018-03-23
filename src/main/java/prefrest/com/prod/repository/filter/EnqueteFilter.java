package prefrest.com.prod.repository.filter;

import lombok.Data;

@Data
public class EnqueteFilter {
    private Long id;
    private String descricao;
    private boolean ativo;
}

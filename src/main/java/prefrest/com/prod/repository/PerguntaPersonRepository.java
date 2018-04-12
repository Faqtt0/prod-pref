package prefrest.com.prod.repository;

import prefrest.com.prod.model.enquetes.Pergunta;

import java.util.List;

public interface PerguntaPersonRepository {
    Pergunta findyByCodigo(Long codigoPergunta);
    boolean removerByEnquete(Long codigo);

}

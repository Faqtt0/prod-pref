package prefrest.com.prod.repository;

import prefrest.com.prod.model.enquetes.Resposta;

import java.util.List;

public interface RespostaRepository {
    List<Resposta> carregarRespostas(Long idEnquete, Long idPergunta);
    Resposta salvarResposta (Resposta resposta);
}

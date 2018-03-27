package prefrest.com.prod.repository;

import prefrest.com.prod.model.enquetes.Resposta;

public interface RespostaRepository {
    Resposta carregarRespostas(Long idEnquete, Long idPergunta);
    Resposta salvarResposta (Resposta resposta);
}

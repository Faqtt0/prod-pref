package prefrest.com.prod.repository;

import prefrest.com.prod.model.enquetes.Resposta;

import java.util.List;

public interface RespostaRepository {
    List<Resposta> carregarRespostas(Long idEnquete, Long idPergunta);
    Resposta salvarResposta (Resposta resposta);
    Resposta atualizarResposta (Resposta resposta);
    Resposta findById (Long id);
    Long incrementaResposta();
    boolean removerRespostaById(Long codigo);
    boolean removerRespostasAll(Long codEnquete);

}

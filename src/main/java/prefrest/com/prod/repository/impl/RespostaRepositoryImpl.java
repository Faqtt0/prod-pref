package prefrest.com.prod.repository.impl;

import org.springframework.stereotype.Service;
import prefrest.com.prod.model.enquetes.Resposta;
import prefrest.com.prod.repository.RespostaRepository;

@Service
public class RespostaRepositoryImpl implements RespostaRepository {
    @Override
    public Resposta carregarRespostas(Long idEnquete, Long idPergunta) {
        return null;
    }

    @Override
    public Resposta salvarResposta(Resposta resposta) {
        return null;
    }
}

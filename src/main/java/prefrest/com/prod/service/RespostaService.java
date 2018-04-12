package prefrest.com.prod.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prefrest.com.prod.event.RecursoEvent;
import prefrest.com.prod.model.DeleteTables;
import prefrest.com.prod.model.enquetes.Pergunta;
import prefrest.com.prod.model.enquetes.Resposta;
import prefrest.com.prod.repository.DeleteTablesRepository;
import prefrest.com.prod.repository.EnquetePersonRepository;
import prefrest.com.prod.repository.RespostaRepository;
import prefrest.com.prod.repository.filter.FiltroPadrao;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RespostaService {
    @Autowired
    EnquetePersonRepository enquetePersonRepository;

    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    DeleteTablesRepository deleteTablesRepository;

    public ResponseEntity<Resposta> salvarResposta(Resposta resposta, HttpServletResponse response, ApplicationEventPublisher publisher) {
        resposta.setUltAlt(LocalDateTime.now());
        if (isEditavelEnquete(resposta)){
            Resposta respostaSalva = respostaRepository.salvarResposta(resposta);
            publisher.publishEvent(new RecursoEvent(this, response, respostaSalva.getCodigo()));
            return ResponseEntity.status(HttpStatus.CREATED).body(respostaSalva);
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<Resposta> atualizarResposta(Long codigo, Resposta resposta) {
        resposta.setUltAlt(LocalDateTime.now());
        if (isEditavelEnquete(resposta)){
            Resposta respostaSalva = respostaRepository.findById(codigo);
            BeanUtils.copyProperties(resposta, respostaSalva, "codigo");
            Resposta respostaAtualizada = respostaRepository.atualizarResposta(resposta);
            if (respostaAtualizada != null) {
                return ResponseEntity.ok().body(respostaAtualizada);
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }


    public boolean removerResposta(Long codigo) {
        Resposta respostaSalva = respostaRepository.findById(codigo);
        if (isEditavelEnquete(respostaSalva)){
            boolean isDelete = respostaRepository.removerRespostaById(codigo);
            Map<String, String> params = new HashMap<>();
            params.put("CODIGO", String.valueOf(respostaSalva.getCodigo()));
            params.put("CODENQUETE", String.valueOf(respostaSalva.getCodEnquete()));
            params.put("CODPERGUNTA", String.valueOf(respostaSalva.getCodPergunta()));
            deleteTablesRepository.save(new DeleteTables(Resposta.class, LocalDateTime.now(), params));
            return isDelete;
        }

        return false;
    }

    private boolean isEditavelEnquete(Resposta respostaSalva) {
        return enquetePersonRepository.isEditavel(respostaSalva.getCodEnquete());
    }


    public ResponseEntity<List<Resposta>> getAllRepostas(FiltroPadrao filtroPadrao) {
        if (filtroPadrao.getAlteracao() != null) {
            List<Resposta> repostas = respostaRepository.findByUltAltAfterOrderByUltAlt(LocalDateTime.parse(filtroPadrao.getAlteracao()));
            return ResponseEntity.ok().body(repostas);
        }
        return ResponseEntity.ok().body(respostaRepository.findAll());
    }
}

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
import prefrest.com.prod.model.enquetes.PerguntaID;
import prefrest.com.prod.model.enquetes.Resposta;
import prefrest.com.prod.repository.*;
import prefrest.com.prod.repository.filter.FiltroPadrao;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PerguntaService {
    @Autowired
    EnquetePersonRepository enquetePersonRepository;

    @Autowired
    PerguntaRepository perguntaRepository;

    @Autowired
    PerguntaPersonRepository perguntaPersonRepository;

    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    DeleteTablesRepository deleteTablesRepository;

    public ResponseEntity<Pergunta> salvarPergunta(Pergunta pergunta, HttpServletResponse response, ApplicationEventPublisher publisher) {
        pergunta.setUltAlt(LocalDateTime.now());
        if (enquetePersonRepository.isEditavel(pergunta.getCodEnquete())) {
            pergunta.setCodigo(perguntaRepository.incrementCodigo());
            Pergunta perguntaSalva = perguntaRepository.save(pergunta);
            publisher.publishEvent(new RecursoEvent(this, response, perguntaSalva.getCodigo()));
            return ResponseEntity.status(HttpStatus.CREATED).body(perguntaSalva);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Pergunta> atualizarPergunta(Long codigo, Pergunta pergunta) {
        pergunta.setUltAlt(LocalDateTime.now());
        if (enquetePersonRepository.isEditavel(pergunta.getCodEnquete())) {
            Pergunta perguntaSalva = perguntaRepository.findOne(new PerguntaID(codigo, pergunta.getCodEnquete()));
            if (perguntaSalva != null) {
                BeanUtils.copyProperties(pergunta, perguntaSalva, "codigo");
                return ResponseEntity.ok().body(perguntaRepository.save(perguntaSalva));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<List<Resposta>> buscarRepostas(Long codigo) {
        return ResponseEntity.ok().body(respostaRepository.findByPergunta(codigo));
    }

    public boolean deletarPerguntas(Long codigo) {
        Pergunta perguntaSalva = perguntaPersonRepository.findyByCodigo(codigo);
        if (enquetePersonRepository.isEditavel(perguntaSalva.getCodEnquete())) {
            respostaRepository.removeRespostasByPergunta(codigo);
            PerguntaID perguntaID = new PerguntaID(perguntaSalva.getCodigo(), perguntaSalva.getCodEnquete());
            perguntaRepository.delete(perguntaID);

            Map<String, String> params = new HashMap<>();
            params.put("CODIGO", String.valueOf(perguntaSalva.getCodigo()));
            params.put("CODENQUETE", String.valueOf(perguntaSalva.getCodEnquete()));
            deleteTablesRepository.save(new DeleteTables(Pergunta.class, LocalDateTime.now(), params));
            return true;
        }
        return false;
    }

    public ResponseEntity<List<Pergunta>> getAllPerguntas(FiltroPadrao filtroPadrao) {
        if (filtroPadrao.getAlteracao() != null){
            return ResponseEntity.ok().body(perguntaRepository.findAllByUltAltAfterOrderByUltAlt(LocalDateTime.parse(filtroPadrao.getAlteracao())));
        }
        return ResponseEntity.ok().body(perguntaRepository.findAll());
    }
}

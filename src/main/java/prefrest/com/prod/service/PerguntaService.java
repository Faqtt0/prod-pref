package prefrest.com.prod.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prefrest.com.prod.event.RecursoEvent;
import prefrest.com.prod.model.enquetes.Enquete;
import prefrest.com.prod.model.enquetes.Pergunta;
import prefrest.com.prod.model.enquetes.PerguntaID;
import prefrest.com.prod.repository.EnquetePersonRepository;
import prefrest.com.prod.repository.EnqueteRepository;
import prefrest.com.prod.repository.PerguntaRepository;

import javax.servlet.http.HttpServletResponse;

@Service
public class PerguntaService {
    @Autowired
    EnquetePersonRepository enquetePersonRepository;

    @Autowired
    PerguntaRepository perguntaRepository;


    public ResponseEntity<Pergunta> salvarPergunta(Pergunta pergunta, HttpServletResponse response, ApplicationEventPublisher publisher) {
        if (enquetePersonRepository.isEditavel(pergunta.getCodEnquete())){
            pergunta.setCodigo(perguntaRepository.incrementCodigo());
            Pergunta perguntaSalva = perguntaRepository.save(pergunta);
            publisher.publishEvent(new RecursoEvent(this, response, perguntaSalva.getCodigo()));
            return ResponseEntity.status(HttpStatus.CREATED).body(perguntaSalva);
        }else {
            return  ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Pergunta> atualizarPergunta(Long codigo, Pergunta pergunta) {
        if (enquetePersonRepository.isEditavel(pergunta.getCodEnquete())){
            Pergunta perguntaSalva = perguntaRepository.findOne(new PerguntaID(codigo, pergunta.getCodEnquete()));
            if (perguntaSalva != null) {
                BeanUtils.copyProperties(pergunta, perguntaSalva, "codigo");
                return  ResponseEntity.ok().body(perguntaRepository.save(perguntaSalva));
            } else{
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
}

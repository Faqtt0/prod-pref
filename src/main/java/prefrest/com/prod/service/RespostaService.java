package prefrest.com.prod.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prefrest.com.prod.event.RecursoEvent;
import prefrest.com.prod.model.enquetes.Resposta;
import prefrest.com.prod.repository.EnquetePersonRepository;
import prefrest.com.prod.repository.RespostaRepository;

import javax.servlet.http.HttpServletResponse;

@Service
public class RespostaService {
    @Autowired
    EnquetePersonRepository enquetePersonRepository;

    @Autowired
    RespostaRepository respostaRepository;


    public ResponseEntity<Resposta> salvarResposta(Resposta resposta, HttpServletResponse response, ApplicationEventPublisher publisher) {
        if (isEditavelEnquete(resposta)){
            Resposta respostaSalva = respostaRepository.salvarResposta(resposta);
            publisher.publishEvent(new RecursoEvent(this, response, respostaSalva.getCodigo()));
            return ResponseEntity.status(HttpStatus.CREATED).body(respostaSalva);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    public ResponseEntity<Resposta> atualizarResposta(Long codigo, Resposta resposta) {

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
            return respostaRepository.removerRespostaById(codigo);
        }

        return false;
    }

    private boolean isEditavelEnquete(Resposta respostaSalva) {
        return enquetePersonRepository.isEditavel(respostaSalva.getCodEnquete());
    }


}

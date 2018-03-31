package prefrest.com.prod.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prefrest.com.prod.exception.EnqueteNaoPermitidaException;
import prefrest.com.prod.model.enquetes.Enquete;
import prefrest.com.prod.model.enquetes.Resposta;
import prefrest.com.prod.repository.EnquetePersonRepository;
import prefrest.com.prod.repository.EnqueteRepository;
import prefrest.com.prod.repository.PerguntaPersonRepository;
import prefrest.com.prod.repository.RespostaRepository;

import java.util.List;

@Service
public class EnqueteService {

    @Autowired
    EnqueteRepository repository;

    @Autowired
    EnquetePersonRepository repositoryPerson;

    @Autowired
    PerguntaPersonRepository perguntaPersonRepository;

    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    RespostaRepository respostaRepository;

    public Enquete salvar(Enquete enquete){
        return verificaSalvaEnquete(enquete);
    }

    private Enquete verificaSalvaEnquete(Enquete enquete) {
        if (enquete.isAtivo()) {
            Integer enqueteAtivo = repositoryPerson.getAtivo();
            if (enqueteAtivo == 0){
                return repository.save(enquete);
            }
        } else {
            Integer dentroInterval = repositoryPerson.dentroDataIntervalo(enquete);
            if (dentroInterval == 0) {
                return repository.save(enquete);
            }
        }
        throw new EnqueteNaoPermitidaException();
    }


    public ResponseEntity<Enquete> carregaEnqueteParcial (Long id) {
        Enquete enquete = repository.findOne(id);
        if (enquete != null){
            return ResponseEntity.ok(repositoryPerson.carregadaDadosEnqueteParcial(enquete));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Resposta>> buscaRespostasPergunta(Long id, Long codigo) {
        return ResponseEntity.ok(respostaRepository.carregarRespostas(id, codigo));
    }


    public ResponseEntity<Enquete> atualizaEnquete(Long id, Enquete enquete) {
        Enquete enqueteSalva = repository.findOne(id);
        if (verificaEnqueteAtualizar(enquete)){
            BeanUtils.copyProperties(enquete, enqueteSalva, "id");
           return  ResponseEntity.ok(repository.save(enqueteSalva));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private boolean verificaEnqueteAtualizar(Enquete enquete) {
        if (enquete.isAtivo()){
            if (repositoryPerson.getAtivo()  == 0) {
                return true;
            }
        } else {
            if (repositoryPerson.dentroDataIntervalo(enquete) == 0) {
                return true;
            }
        }

        return false;
    }

    public boolean deletarEnquete(Long codigo) {
        if (repositoryPerson.isEditavel(codigo)){

            return true;
        }
        return false;
    }
}

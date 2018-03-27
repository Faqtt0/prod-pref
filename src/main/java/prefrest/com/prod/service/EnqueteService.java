package prefrest.com.prod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prefrest.com.prod.exception.EnqueteNaoPermitidaException;
import prefrest.com.prod.model.enquetes.Enquete;
import prefrest.com.prod.repository.EnquetePersonRepository;
import prefrest.com.prod.repository.EnqueteRepository;

@Service
public class EnqueteService {

    @Autowired
    EnqueteRepository repository;

    @Autowired
    EnquetePersonRepository repositoryPerson;


    public Enquete salvar(Enquete enquete){
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
}

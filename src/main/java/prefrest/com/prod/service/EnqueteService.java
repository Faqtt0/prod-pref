package prefrest.com.prod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prefrest.com.prod.exception.EnqueteNaoPermitidaException;
import prefrest.com.prod.model.enquetes.Enquetes;
import prefrest.com.prod.repository.EnquetePersonRepository;
import prefrest.com.prod.repository.EnqueteRepository;

@Service
public class EnqueteService {

    @Autowired
    EnqueteRepository repository;

    @Autowired
    EnquetePersonRepository repositoryPerson;

    public Enquetes salvar(Enquetes enquetes){
        if (enquetes.isAtivo()) {
            Integer enqueteAtivo = repositoryPerson.getAtivo();
            if (enqueteAtivo == 0){
                return repository.save(enquetes);
            }
        } else {
            Integer dentroInterval = repositoryPerson.dentroDataIntervalo(enquetes);
            if (dentroInterval == 0) {
                return repository.save(enquetes);
            }
        }

        throw new EnqueteNaoPermitidaException();
    }

}

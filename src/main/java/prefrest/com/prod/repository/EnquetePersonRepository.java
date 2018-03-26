package prefrest.com.prod.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import prefrest.com.prod.model.enquetes.Enquetes;
import prefrest.com.prod.repository.filter.EnqueteFilter;

import java.util.List;

public interface EnquetePersonRepository  {
    List<Enquetes> filtrarEnquetes(EnqueteFilter enqueteFilter);
    Enquetes atualizarEnquete (Long codigo, Enquetes enquetes);
    void removerEnquete();
    Integer getAtivo();
    Integer dentroDataIntervalo (Enquetes enquete);

}

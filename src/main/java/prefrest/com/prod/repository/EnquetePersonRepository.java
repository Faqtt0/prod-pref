package prefrest.com.prod.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import prefrest.com.prod.model.enquetes.Enquetes;
import prefrest.com.prod.repository.filter.EnqueteFilter;

import java.util.List;

@Component
public interface EnquetePersonRepository  {
    Enquetes salvarEnquete (Enquetes enquetes);
    List<Enquetes> filtrarEnquetes(EnqueteFilter enqueteFilter);
    Enquetes atualizarEnquete (Long codigo, Enquetes enquetes);
    void removerEnquete();
}

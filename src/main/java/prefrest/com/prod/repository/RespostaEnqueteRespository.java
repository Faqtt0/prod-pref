package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.enquetes.RespostaEnquete;
import prefrest.com.prod.repository.filter.FiltroRespostaEnquete;

public interface RespostaEnqueteRespository extends JpaRepository<RespostaEnquete, Long> {
    RespostaEnquete findByCpfAndIdEnquete(String cpf, Long idEnquete);
}

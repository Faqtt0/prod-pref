package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prefrest.com.prod.model.enquetes.RespostaEnquete;
import prefrest.com.prod.repository.filter.FiltroRespostaEnquete;

public interface RespostaEnqueteRespository extends JpaRepository<RespostaEnquete, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM RESPOSTAENQUETE WHERE CPF = ?2 AND IDENQUETE = ?1 LIMIT 1")
    RespostaEnquete findByIdEnqueteAndCpf(Long idEnquete, String cpf);
}

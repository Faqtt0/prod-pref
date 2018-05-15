package prefrest.com.prod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import prefrest.com.prod.model.enquetes.RespostaEnquete;
import prefrest.com.prod.repository.RespostaEnqueteRespository;
import prefrest.com.prod.repository.filter.FiltroRespostaEnquete;

import java.util.List;

@Service
public class RespostaEnqueteService {

    @Autowired
    NamedParameterJdbcTemplate template;

    @Autowired
    RespostaEnqueteRespository respository;

    public ResponseEntity isRespostaEnquete(FiltroRespostaEnquete filtro) {
        RespostaEnquete cpfAtivo = respository.findByCpfAndIdEnquete(filtro.getCpf(), filtro.getIdEnquete());
        if (cpfAtivo != null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity salvarRespostasEnquete(List<RespostaEnquete> respostas) {
        respostas.forEach(respostaEnquete -> respository.save(respostaEnquete));
        return ResponseEntity.ok().build();
    }
}

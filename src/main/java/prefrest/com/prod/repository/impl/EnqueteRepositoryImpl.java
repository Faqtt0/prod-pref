package prefrest.com.prod.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import prefrest.com.prod.model.enquetes.Enquetes;
import prefrest.com.prod.repository.EnquetePersonRepository;
import prefrest.com.prod.repository.EnqueteRepository;
import prefrest.com.prod.repository.filter.EnqueteFilter;

import java.util.List;

public class EnqueteRepositoryImpl implements EnquetePersonRepository {

    @Autowired
    EnqueteRepository enqueteRepository;

    @Autowired
    JdbcTemplate template;

    @Autowired
    NamedParameterJdbcTemplate paramTemplate;

    @Override
    public Enquetes salvarEnquete(Enquetes enquete) {
        String sqlAtivo = "SELECT COUNT(ATIVO) FROM ENQUETE WHERE ATIVO = TRUE ";
        String sqlDataIntervalo = "SELECT COUNT(*) FROM ENQUETE WHERE DATAFIM > :DTINI";
        Integer rows = 0;
        MapSqlParameterSource parameterSource;


        if (enquete.isAtivo()){
             rows = template.queryForObject(sqlAtivo, Integer.class);
             if (rows == 0) {
                 return enqueteRepository.save(enquete);
             }
        } else {
            parameterSource = new MapSqlParameterSource().addValue("DTINI", enquete.getDataIni());
            rows = paramTemplate.queryForObject(sqlDataIntervalo, parameterSource, Integer.class);
            if (rows == 0) {
                return enqueteRepository.save(enquete);
            }
        }

        return null;
    }

    @Override
    public List<Enquetes> filtrarEnquetes(EnqueteFilter enqueteFilter) {
        if (enqueteFilter.getId() != null){
            return enqueteRepository.findByIdOrderByIdDesc(enqueteFilter.getId());
        } else if (enqueteFilter.getDescricao() != null) {
            return enqueteRepository.findByDescricaoContainingOrderByDescricao(enqueteFilter.getDescricao());
        } else if (enqueteFilter.isAtivo()) {
            return enqueteRepository.findByAtivoOrderByIdDesc();
        }

        return enqueteRepository.findByEnquetesLimit();
    }

    @Override
    public Enquetes atualizarEnquete(Long codigo, Enquetes enquetes) {
        return null;
    }

    @Override
    public void removerEnquete() {

    }
}

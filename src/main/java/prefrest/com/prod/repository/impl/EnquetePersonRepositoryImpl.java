package prefrest.com.prod.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import prefrest.com.prod.model.enquetes.Enquetes;
import prefrest.com.prod.repository.EnquetePersonRepository;
import prefrest.com.prod.repository.EnqueteRepository;
import prefrest.com.prod.repository.filter.EnqueteFilter;

import java.util.List;

@Service
public class EnquetePersonRepositoryImpl implements EnquetePersonRepository {

    @Autowired
    EnqueteRepository enqueteRepository;

    @Autowired
    JdbcTemplate template;

    @Autowired
    NamedParameterJdbcTemplate paramTemplate;

    private MapSqlParameterSource parameterSource;


    public Integer getAtivo (){
        String sqlAtivo = "SELECT COUNT(ATIVO) FROM ENQUETE WHERE ATIVO = TRUE ";
        return template.queryForObject(sqlAtivo, Integer.class);
    }

    public Integer dentroDataIntervalo (Enquetes enquete){
        String sqlDataIntervalo = "SELECT COUNT(*) FROM ENQUETE WHERE DATAFIM > :DTINI";
        parameterSource = new MapSqlParameterSource().addValue("DTINI", enquete.getDataIni());
        return paramTemplate.queryForObject(sqlDataIntervalo, parameterSource, Integer.class);
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

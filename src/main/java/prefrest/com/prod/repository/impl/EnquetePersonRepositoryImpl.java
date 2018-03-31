package prefrest.com.prod.repository.impl;

import org.omg.CORBA.DATA_CONVERSION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import prefrest.com.prod.model.enquetes.Enquete;
import prefrest.com.prod.model.enquetes.Pergunta;
import prefrest.com.prod.repository.EnquetePersonRepository;
import prefrest.com.prod.repository.EnqueteRepository;
import prefrest.com.prod.repository.PerguntaRepository;
import prefrest.com.prod.repository.filter.EnqueteFilter;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnquetePersonRepositoryImpl implements EnquetePersonRepository {

    @Autowired
    EnqueteRepository enqueteRepository;

    @Autowired
    PerguntaRepository perguntaRepository;

    @Autowired
    JdbcTemplate template;

    @Autowired
    NamedParameterJdbcTemplate paramTemplate;

    private MapSqlParameterSource parameterSource;


    public Integer getAtivo (){
        String sqlAtivo = "SELECT COUNT(ATIVO) FROM ENQUETE WHERE ATIVO = TRUE ";
        return template.queryForObject(sqlAtivo, Integer.class);
    }

    public Integer dentroDataIntervalo (Enquete enquete){
        String sqlDataIntervalo = "SELECT COUNT(*) FROM ENQUETE WHERE DATAFIM >= :DTINI";
        parameterSource = new MapSqlParameterSource().addValue("DTINI", enquete.getDataIni());
        return paramTemplate.queryForObject(sqlDataIntervalo, parameterSource, Integer.class);
    }


    @Override
    public List<Enquete> filtrarEnquetes(EnqueteFilter enqueteFilter) {
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
    public void removerEnquete() {

    }

    @Override
    public Enquete carregadaDadosEnqueteParcial(Enquete enquete) {
        String sql = "SELECT P.* FROM PERGUNTA P WHERE P.CODENQUETE = :ID";
        parameterSource = new MapSqlParameterSource().addValue("ID", enquete.getId());
        enquete.setPerguntas(paramTemplate.query(sql, parameterSource, new BeanPropertyRowMapper<>(Pergunta.class)));
        return enquete;
    }

    @Override
    public boolean isEditavel(Long idEnquete) {
        Enquete enquete = enqueteRepository.findOne(idEnquete);

        if (enquete.isAtivo() && getAtivo() > 0) {
            return true;
        } else if (!enquete.isAtivo() && enquete.getDataIni() != null) {
            return isEnqueteEditData(enquete.getDataIni(), enquete.getDataFim());
        } else {
            return true;
        }

    }

    private boolean isEnqueteEditData(LocalDate dataIni, LocalDate dataFim) {
        if (dataIni.isBefore(LocalDate.now()) && LocalDate.now().isBefore(dataFim)) {
            return true;
        } else if (dataIni.isBefore(LocalDate.now())) {
            return false;
        }
        return true;
    }
}

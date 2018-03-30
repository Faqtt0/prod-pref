package prefrest.com.prod.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import prefrest.com.prod.model.enquetes.Resposta;
import prefrest.com.prod.repository.RespostaRepository;

import java.util.List;
import java.util.Map;

@Service
public class RespostaRepositoryImpl implements RespostaRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate template;

    @Override
    public List<Resposta> carregarRespostas(Long idEnquete, Long idPergunta) {
        String sql = "SELECT * FROM RESPOSTA WHERE CODENQUETE = :ENQUETE AND CODPERGUNTA = :PERGUNTA";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("ENQUETE", idEnquete)
                .addValue("PERGUNTA", idPergunta);
        return template.query(sql, params, new BeanPropertyRowMapper<>(Resposta.class));
    }

    @Override
    public Resposta salvarResposta(Resposta resposta) {
        Long respostaCod = incrementaResposta();
        String sqlInsert = "INSERT INTO RESPOSTA (CODIGO, CODPERGUNTA, CODENQUETE, RESPOSTA) VALUES (:COD, :CODPERG, :CODENQ, :RESP)";
        MapSqlParameterSource params = getMapSqlParameterSourceResposta(resposta, respostaCod);
        template.update(sqlInsert, params);
        return findById(respostaCod);
    }

    private MapSqlParameterSource getMapSqlParameterSourceResposta(Resposta resposta, Long respostaCod) {
        return new MapSqlParameterSource().addValue("COD", respostaCod)
                    .addValue("CODPERG", resposta.getCodPergunta())
                    .addValue("CODENQ", resposta.getCodEnquete())
                    .addValue("RESP", resposta.getResposta());
    }

    @Override
    public Long incrementaResposta() {
        String sqlMaxResposta = "SELECT COALESCE(MAX(CODIGO),0) + 1 AS CODIGO FROM RESPOSTA";
        return jdbcTemplate.queryForObject(sqlMaxResposta, Long.class);
    }

    @Override
    public Resposta findById(Long id) {
        String sql = "SELECT * FROM RESPOSTA WHERE CODIGO = :COD";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("COD", id);
        return template.queryForObject(sql, params, new BeanPropertyRowMapper<>(Resposta.class));
    }

    @Override
    public Resposta atualizarResposta(Resposta resposta) {
        String sql = "UPDATE RESPOSTA SET CODPERGUNTA =:CODPERG, CODENQUETE = :CODENQ, RESPOSTA = :RESP WHERE CODIGO = :COD";
        MapSqlParameterSource params =  getMapSqlParameterSourceResposta(resposta, resposta.getCodigo());
        template.update(sql, params);
        return findById(resposta.getCodigo());
    }

    @Override
    public boolean removerRespostaById(Long codigo) {
        String sql = "DELETE FROM RESPOSTA WHERE RESPOSTA = :CODIGO";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("CODIGO", codigo);
        return template.update(sql, params) > 0;
    }

    @Override
    public boolean removerRespostasAll(Long codEnquete) {
        String sql = "DELETE FROM RESPOSTA WHERE CODENQUETE = :ENQUETE";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("ENQUETE", codEnquete);
        return template.update(sql, params) > 0;
    }
}

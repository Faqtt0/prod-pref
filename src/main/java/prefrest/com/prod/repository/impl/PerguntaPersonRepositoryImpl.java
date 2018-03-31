package prefrest.com.prod.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import prefrest.com.prod.model.enquetes.Pergunta;
import prefrest.com.prod.repository.PerguntaPersonRepository;

@Service
public class PerguntaPersonRepositoryImpl implements PerguntaPersonRepository {
    @Autowired
    NamedParameterJdbcTemplate template;

    @Override
    public Pergunta findyByCodigo(Long codigoPergunta) {
        String sql =  "SELECT * FROM PERGUNTA WHERE CODIGO = :CODIGO";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("CODIGO", codigoPergunta);
        return template.queryForObject(sql, params, new BeanPropertyRowMapper<>(Pergunta.class));
    }
}

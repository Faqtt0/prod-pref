package prefrest.com.prod.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import prefrest.com.prod.model.enquetes.Resposta;
import prefrest.com.prod.repository.RespostaRepository;

import java.util.List;

@Service
public class RespostaRepositoryImpl implements RespostaRepository {
    @Autowired
    NamedParameterJdbcTemplate template;

    @Override
    public List<Resposta> carregarRespostas(Long idEnquete, Long idPergunta) {
        String sql = "SELECT * FROM RESPOSTA WHERE CODENQUETE = :ENQUETE AND CODPERGUNTA = :PERGUNTA";
        MapSqlParameterSource params =  new MapSqlParameterSource().addValue("ENQUETE", idEnquete)
                .addValue("PERGUNTA", idPergunta);
        return template.query(sql, params, new BeanPropertyRowMapper<>(Resposta.class));
    }

    @Override
    public Resposta salvarResposta(Resposta resposta) {
        return null;
    }
}

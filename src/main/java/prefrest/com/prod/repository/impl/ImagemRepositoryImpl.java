package prefrest.com.prod.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import prefrest.com.prod.model.Imagem;
import prefrest.com.prod.repository.ImagemRepository;

@Service
public class ImagemRepositoryImpl implements ImagemRepository {

    @Autowired
    NamedParameterJdbcTemplate template;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private Long maxIdIncrement() {
        String sql = "SELECT COALESCE(MAX(ID),0) + 1 FROM IMAGEM";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    @Override
    public Imagem findById(Long id) {
        String sql = "SELECT * FROM IMAGEM WHERE ID = :ID";
        MapSqlParameterSource params =  new MapSqlParameterSource().addValue("ID", id);
        return template.queryForObject(sql, params, new BeanPropertyRowMapper<>(Imagem.class));
    }

    @Override
    public Imagem findByDiretorio(String diretorio) {
        String sql = "SELECT * FROM IMAGEM WHERE CAMINHO = :";
        return null;
    }

    @Override
    public boolean updateImagem(Imagem imagem) {
        String sql =  "UPDATE IMAGEM SET CAMINHO = :CAMINHO WHERE ID = :ID";
        MapSqlParameterSource params =  new MapSqlParameterSource().addValue("CAMINHO", imagem.getCaminho())
                .addValue("ID", imagem.getId());
        return template.update(sql, params) > 0;
    }

    @Override
    public Imagem insertImagem(String caminho) {
        Long idImagem = maxIdIncrement();
        String sql = "INSERT INTO IMAGEM (ID, CAMINHO) VALUES (:ID, :CAMINHO)";
        MapSqlParameterSource params =  new MapSqlParameterSource().addValue("ID", idImagem)
                .addValue("CAMINHO", caminho);
        template.update(sql, params);
        return findById(idImagem);
    }

    @Override
    public boolean deleteById(Long codigo) {
        return false;
    }
}

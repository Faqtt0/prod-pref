package prefrest.com.prod.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import prefrest.com.prod.model.Imagens;
import prefrest.com.prod.repository.ImagemRepositoryPerson;

import java.time.LocalDateTime;

@Service
public class ImagemRepositoryPersonImpl implements ImagemRepositoryPerson {
    @Autowired
    NamedParameterJdbcTemplate template;

    @Deprecated
    public boolean updateImagem(String caminhoImagem, Imagens imagem) {
        String sql =  "UPDATE IMAGENS SET IMAGEM = :IMAGEM, ULTALT = :ULTALT WHERE ID = :ID";
        imagem.setUltAlt(LocalDateTime.now());
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("IMAGEM", caminhoImagem)
                .addValue("ULTALT", imagem.getUltAlt())
                .addValue("ID", imagem.getId());
        return template.update(sql, params) > 0;
    }

    @Override
    public boolean updateImagemInfos(Imagens imagem) {
        String sql = "UPDATE IMAGENS SET DESCRICAO = :DESC, ORDEM = :ORDEM, ULTALT = :ULTALT WHERE ID = :ID";
        imagem.setUltAlt(LocalDateTime.now());
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("DESC", imagem.getDescricao())
                .addValue("ORDEM", imagem.getOrdem())
                .addValue("ULTALT", imagem.getUltAlt())
                .addValue("ID", imagem.getId());
        return template.update(sql, params) > 0;
    }

    /*
    @Deprecated
    public List<Imagens> getImagens(FiltroPadrao filter) {
        StringBuilder sbSql = new StringBuilder("SELECT * FROM IMAGENS ");
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (filter.getAlteracao() != null){
            sbSql.append("WHERE ULTALT >= :ULTALT ");
            params.addValue("ULTALT", LocalDateTime.parse(filter.getAlteracao()));
        }
        sbSql.append("ORDER BY ULTALT");

        return template.query(sbSql.toString(), params, new BeanPropertyRowMapper<>(Imagens.class));
    }*/

}

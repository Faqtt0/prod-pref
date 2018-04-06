package prefrest.com.prod.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import prefrest.com.prod.model.Agenda;
import prefrest.com.prod.model.Imagens;
import prefrest.com.prod.model.ImagensHistoricas;
import prefrest.com.prod.repository.ImagemCommonRepository;
import prefrest.com.prod.repository.filter.ImagensFilter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ImagemCommonRepositoryImpl<T> implements ImagemCommonRepository {
    @Autowired
    NamedParameterJdbcTemplate template;
    private StringBuilder sbSql;

    @Override
    public boolean updateImagem(String caminhoImagem, Object img) throws InvocationTargetException, IllegalAccessException {
        sbSql = new StringBuilder();
        Class imagem = img.getClass();

        if (isEntity(imagem)){
            Table table = getTable(imagem);

            sbSql.append("UPDATE ").append(table.name()).append(" SET IMAGEM = :IMAGEM, ULTALT = :ULTALT WHERE ID = :ID");
            Long idImagem = null;

            for (Method m : imagem.getMethods()) {
                if (m.getName().startsWith("getId") && m.getReturnType() == Long.class){
                    idImagem = (Long) m.invoke(img);
                    break;
                }
            }

            MapSqlParameterSource params = new MapSqlParameterSource().addValue("IMAGEM", caminhoImagem)
                    .addValue("ULTALT", LocalDateTime.now())
                    .addValue("ID",idImagem);
            return template.update(sbSql.toString(), params) > 0;
        }
        return false;
    }

    private Table getTable(Class imagem) {
        return (Table) imagem.getAnnotation(Table.class);
    }

    private boolean isEntity(Class imagem) {
        return imagem.getAnnotation(Entity.class) != null;
    }

    @Override
    public List<T> getImagens(ImagensFilter filter, Object clazz) {
        StringBuilder sbSql = new StringBuilder("SELECT * FROM ");
        if (isEntity(clazz.getClass())){
            Table table = getTable(clazz.getClass());
            sbSql.append(table.name());
        } else {
            sbSql.append(clazz.getClass().getSimpleName());
        }

        MapSqlParameterSource params = new MapSqlParameterSource();
        if (filter.getAlteracao() != null){
            sbSql.append("WHERE ULTALT >= :ULTALT ");
            params.addValue("ULTALT", LocalDateTime.parse(filter.getAlteracao()));
        }
        sbSql.append("ORDER BY ULTALT");

        return template.query(sbSql.toString(), params, new BeanPropertyRowMapper<>());
    }


}

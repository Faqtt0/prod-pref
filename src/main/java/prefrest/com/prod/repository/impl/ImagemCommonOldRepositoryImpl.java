package prefrest.com.prod.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import prefrest.com.prod.repository.ImagemCommonOldRepository;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;


@Service
public class ImagemCommonOldRepositoryImpl implements ImagemCommonOldRepository {
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


    /*@Override
    public List getImagens(FiltroPadrao filter, Class<?> clazz) {
        StringBuilder sbSql = new StringBuilder("SELECT * FROM ");
        if (isEntity(clazz)){
            Table table = getTable(clazz);
            sbSql.append(table.name());
        } else {
            sbSql.append(clazz.getSimpleName());
        }

        MapSqlParameterSource params = new MapSqlParameterSource();
        if (filter.getAlteracao() != null){
            sbSql.append(" WHERE ULTALT >= :ULTALT ");
            params.addValue("ULTALT", LocalDateTime.parse(filter.getAlteracao()));
        }
        sbSql.append("ORDER BY ULTALT");

        return template.query(sbSql.toString(), params, new BeanPropertyRowMapper<>(clazz));
    }*/

    private Table getTable(Class imagem) {
        return (Table) imagem.getAnnotation(Table.class);
    }

    private boolean isEntity(Class imagem) {
        return imagem.getAnnotation(Entity.class) != null;
    }

}

package prefrest.com.prod.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import prefrest.com.prod.model.Folder;
import prefrest.com.prod.repository.CommomRepository;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CommomRepositoryImpl implements CommomRepository {

    @Autowired
    NamedParameterJdbcTemplate template;

    @Override
    public Long recuperaCodImagem(Long codigo, Class<?> clazz) {
        StringBuilder sbSql = new StringBuilder("SELECT CODIMAGEM FROM ");
        getTabela(clazz, sbSql);
        sbSql.append(" WHERE ID = :ID");
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("ID", codigo);
        return template.queryForObject(sbSql.toString(), params, new BeanPropertyRowMapper<>(Long.class));
    }

    @Override
    public boolean updateCodImagem(Long codigo, Long codImagem, Class<?> clazz) {
        StringBuilder sbSql = new StringBuilder("UPDATE ");
        getTabela(clazz, sbSql);
        sbSql.append(" SET CODIMAGEM = :CODIMAGEM WHERE ID = :ID");
        MapSqlParameterSource parmas = new MapSqlParameterSource().addValue("CODIMAGEM", codImagem)
                .addValue("ID", codigo);
        return template.update(sbSql.toString(), parmas) > 0;
    }

    @Override
    public boolean updateUltAlt(Class<?> clazz, LocalDateTime ultAlt, Long id) {
        StringBuilder sbSql = new StringBuilder("UPDATE ");
        if (isEntity(clazz)){
            Table tabela = getTable(clazz);
            sbSql.append(tabela.name());
        } else {
            sbSql.append(clazz.getSimpleName());
        }
        sbSql.append(" SET ULTALT = :ULTALT WHERE ID = :ID");
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("ULTALT", ultAlt)
                .addValue("ID", id);
        return template.update(sbSql.toString(), params) > 0;
    }

    private void getTabela(Class<?> clazz, StringBuilder sbSql) {
        if (isEntity(clazz)) {
            Table table = getTable(clazz);
            sbSql.append(table.name());
        } else {
            sbSql.append(clazz.getSimpleName());
        }
    }


    public static boolean isEntity(Class clazz) {
        return clazz.getAnnotation(Entity.class) != null;
    }

    public static Table getTable(Class clazz) {
        return (Table) clazz.getAnnotation(Table.class);
    }


}

package prefrest.com.prod.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import prefrest.com.prod.model.Folder;
import prefrest.com.prod.repository.usuario.FolderPersonRepository;

import java.util.List;

@Service
public class FolderPersonRespositoryImpl implements FolderPersonRepository {

    @Autowired
    NamedParameterJdbcTemplate template;

    @Override
    public void updateListaOrdemImagem(List<Folder> lista) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("UPDATE FOLDER SET ORDEM = :ORDEM WHERE ID = :ID");
        lista.forEach(folder -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("ORDEM", folder.getOrdem()).addValue("ID", folder.getId());

            int rows = template.update(sbSql.toString(), params);
            if (rows <= 0){
                throw new IllegalArgumentException();
            }
        });
    }
}

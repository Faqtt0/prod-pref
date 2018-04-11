package prefrest.com.prod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prefrest.com.prod.model.DeleteTables;
import prefrest.com.prod.repository.DeleteTablesRepository;
import prefrest.com.prod.repository.filter.FiltroPadrao;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeleteTableService {

    @Autowired
    DeleteTablesRepository deleteTablesRepository;

    public ResponseEntity<List<DeleteTables>> buscarDadosDeletadosTabelas() {
        return ResponseEntity.ok().body(deleteTablesRepository.getRegistrosOrdemByTabela());
    }


    public void deletarRegistrosSincronizados(FiltroPadrao filtroPadrao) {
        deleteTablesRepository.deleteByUltAltBefore(LocalDateTime.parse(filtroPadrao.getAlteracao()));
    }
}

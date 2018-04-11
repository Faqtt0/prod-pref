package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.model.DeleteTables;
import prefrest.com.prod.repository.filter.FiltroPadrao;
import prefrest.com.prod.service.DeleteTableService;

import java.util.List;

@RestController
@RequestMapping("/infosdelete")
public class DeleteTablesResource {

    @Autowired
    DeleteTableService deleteTableService;

    @GetMapping()
    public ResponseEntity<List<DeleteTables>> retornaTabelas(){
        return deleteTableService.buscarDadosDeletadosTabelas();
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletaRegistros (FiltroPadrao padrao){
        deleteTableService.deletarRegistrosSincronizados(padrao);
    }
}

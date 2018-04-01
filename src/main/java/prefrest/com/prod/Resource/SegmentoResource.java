package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.model.empresas.Empresa;
import prefrest.com.prod.model.empresas.Segmento;
import prefrest.com.prod.repository.EmpresasRespository;
import prefrest.com.prod.repository.SegmentoRepository;

import java.util.List;

@RestController
@RequestMapping("/segmento")
public class SegmentoResource {

    @Autowired
    SegmentoRepository segmentoRepository;

    @Autowired
    EmpresasRespository empresasRespository;

    @GetMapping()
    public ResponseEntity<List<Segmento>> getAllSegmentos(){
        return ResponseEntity.ok(segmentoRepository.findAll());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<List<Empresa>> getEmpresasSegmento(){
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(empresasRespository.findAll());
    }

    @PutMapping()
    public ResponseEntity<Segmento> atulizarSegmento(){
        //TODO atualizar segmento
        return null;
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity removerSegmento(){
        //TODO deletar Segmento
        return null;
    }


}

package prefrest.com.prod.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.model.empresas.Empresa;

@RestController
@RequestMapping("/empresas")
public class EmpresaResource {

    @PostMapping()
    public ResponseEntity<Empresa> cadastrarEmpresa() {
        return  null;
    }

    @PutMapping()
    public ResponseEntity atualizarEmpresa() {
        return  null;
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarEmpresa() {

    }
}

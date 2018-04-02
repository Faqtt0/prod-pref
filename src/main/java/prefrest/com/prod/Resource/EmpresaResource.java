package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.model.empresas.Empresa;
import prefrest.com.prod.repository.EmpresaRespository;
import prefrest.com.prod.service.EmpresaService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/empresas")
public class EmpresaResource {

    @Autowired
    EmpresaRespository empresaRespository;

    @Autowired
    EmpresaService service;

    @Autowired
    ApplicationEventPublisher publisher;

    @PostMapping()
    public ResponseEntity<Empresa> cadastrarEmpresa(@Valid @RequestBody Empresa empresa, HttpServletResponse response) {
        return service.cadastrarEmpresa(empresa, response, publisher);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity atualizarEmpresa(@PathVariable Long codigo,
                                           @Valid @RequestBody Empresa empresa) {
        return  service.atualizarEmpresa(codigo, empresa);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarEmpresa(@PathVariable Long codigo) {
        empresaRespository.delete(codigo);
    }
}

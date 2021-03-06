package prefrest.com.prod.Resource.empresas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.model.empresas.Empresa;
import prefrest.com.prod.repository.EmpresaRespository;
import prefrest.com.prod.repository.filter.FiltroPadrao;
import prefrest.com.prod.service.EmpresaService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaResource {

    @Autowired
    EmpresaRespository empresaRespository;

    @Autowired
    EmpresaService service;

    @Autowired
    ApplicationEventPublisher publisher;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_EMPRESA') and #oauth2.hasScope('read')")
    public ResponseEntity<List<Empresa>> recuperaEmpresas(FiltroPadrao filtroPadrao){
        return service.getAllEmpresas(filtroPadrao);
    }


    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_EMPRESA') and #oauth2.hasScope('write')")
    public ResponseEntity<Empresa> cadastrarEmpresa(@Valid @RequestBody Empresa empresa, HttpServletResponse response) {
        return service.cadastrarEmpresa(empresa, response, publisher);
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_ATUALIZAR_EMPRESA') and #oauth2.hasScope('write')")
    public ResponseEntity atualizarEmpresa(@PathVariable Long codigo,
                                           @Valid @RequestBody Empresa empresa) {
        return  service.atualizarEmpresa(codigo, empresa);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_DELETAR_EMPRESA') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarEmpresa(@PathVariable Long codigo) {
        service.deletarEmpresa(codigo);
    }
}

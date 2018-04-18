package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prefrest.com.prod.repository.filter.FiltroPadrao;
import prefrest.com.prod.service.CargaInicialService;

import java.util.Map;

@RestController
@RequestMapping("/cargainicial")
public class CargaInicialResource {

    @Autowired
    CargaInicialService service;

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CARGAINICIAL') and #oauth2.hasScope('read')")
    public ResponseEntity<Map<String, Object>> recuperaDadosTotem(FiltroPadrao filtroPadrao){
        return service.getAllInfos(filtroPadrao);
    };
}

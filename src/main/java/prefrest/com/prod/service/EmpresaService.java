package prefrest.com.prod.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prefrest.com.prod.event.RecursoEvent;
import prefrest.com.prod.model.DeleteTables;
import prefrest.com.prod.model.empresas.Empresa;
import prefrest.com.prod.repository.DeleteTablesRepository;
import prefrest.com.prod.repository.EmpresaRespository;
import prefrest.com.prod.repository.filter.FiltroPadrao;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRespository empresaRespository;

    @Autowired
    private DeleteTablesRepository deleteTablesRepository;


    public ResponseEntity<Empresa> cadastrarEmpresa(Empresa empresa, HttpServletResponse response, ApplicationEventPublisher publisher) {
        empresa.setUltAlt(LocalDateTime.now());
        Empresa empresaSalva = empresaRespository.save(empresa);
        publisher.publishEvent(new RecursoEvent(this, response, empresaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
    }

    public ResponseEntity atualizarEmpresa(Long codigo, Empresa empresa) {
        empresa.setUltAlt(LocalDateTime.now());
        Empresa empresaSalva = empresaRespository.findOne(codigo);
        BeanUtils.copyProperties(empresa, empresaSalva, "CODIGO");
        Empresa empresaAtualizada = empresaRespository.save(empresa);
        if (empresaAtualizada != null){
            return ResponseEntity.ok(empresaAtualizada);
        }
        return ResponseEntity.badRequest().build();
    }

    public void deletarEmpresa(Long codigo) {
        empresaRespository.delete(codigo);
        Map<String, String> params = new HashMap<>();
        params.put("CODIGO", String.valueOf(codigo));
        deleteTablesRepository.save(new DeleteTables(Empresa.class, LocalDateTime.now(), params));


    }

    public ResponseEntity<List<Empresa>> getAllEmpresas(FiltroPadrao filtroPadrao) {
        if (filtroPadrao.getAlteracao() != null){
            List<Empresa> empresas = empresaRespository.findAllByUltAltAfterOrderByUltAlt(LocalDateTime.parse(filtroPadrao.getAlteracao()));
            return ResponseEntity.ok().body(empresas);
        }
        return ResponseEntity.ok().body(empresaRespository.findAllOrderByDescricaoDesc());
    }
}

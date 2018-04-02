package prefrest.com.prod.service;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prefrest.com.prod.event.RecursoEvent;
import prefrest.com.prod.model.empresas.Empresa;
import prefrest.com.prod.repository.EmpresaRespository;

import javax.servlet.http.HttpServletResponse;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRespository empresaRespository;


    public ResponseEntity<Empresa> cadastrarEmpresa(Empresa empresa, HttpServletResponse response, ApplicationEventPublisher publisher) {
        Empresa empresaSalva = empresaRespository.save(empresa);
        publisher.publishEvent(new RecursoEvent(this, response, empresaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
    }

    public ResponseEntity atualizarEmpresa(Long codigo, Empresa empresa) {
        Empresa empresaSalva = empresaRespository.findOne(codigo);
        BeanUtils.copyProperties(empresa, empresaSalva, "CODIGO");
        Empresa empresaAtualizada = empresaRespository.save(empresa);
        if (empresaAtualizada != null){
            return ResponseEntity.ok(empresaAtualizada);
        }
        return ResponseEntity.badRequest().build();
    }
}

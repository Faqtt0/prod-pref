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
import prefrest.com.prod.model.empresas.Segmento;
import prefrest.com.prod.repository.DeleteTablesRepository;
import prefrest.com.prod.repository.EmpresaRespository;
import prefrest.com.prod.repository.SegmentoRepository;
import prefrest.com.prod.repository.filter.FiltroPadrao;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SegmentoService {

    @Autowired
    SegmentoRepository segmentoRepository;

    @Autowired
    EmpresaRespository empresaRespository;

    @Autowired
    DeleteTablesRepository deleteTablesRepository;


    public ResponseEntity<Segmento> atualizarSegmento(Long codigo, Segmento segmento) {
        segmento.setUltAlt(LocalDateTime.now());
        Segmento segmentoSalvo = segmentoRepository.findOne(codigo);
        BeanUtils.copyProperties(segmento, segmentoSalvo, "id");
        return ResponseEntity.ok(segmentoRepository.save(segmentoSalvo));
    }

    public ResponseEntity<Segmento> criarSegmento(Segmento segmento, ApplicationEventPublisher publisher, HttpServletResponse response) {
        segmento.setUltAlt(LocalDateTime.now());
        Segmento segmentoSalvo = segmentoRepository.save(segmento);
        publisher.publishEvent(new RecursoEvent(this, response, segmentoSalvo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(segmentoSalvo);
    }

    public void deletarSegmentoEmpresa(Long codigo) {
        empresaRespository.deleteByCodSegmento(codigo);
        segmentoRepository.delete(codigo);
        Map<String, String> params = new HashMap<>();
        params.put("ID", String.valueOf(codigo));
        deleteTablesRepository.save(new DeleteTables(Segmento.class, LocalDateTime.now(), params));
    }

    public ResponseEntity<List<Segmento>> getAllSegByUltAlt(FiltroPadrao filtroPadrao) {
        if (filtroPadrao.getAlteracao() != null){
            List<Segmento> segmentos = segmentoRepository.findAllByUltAltAfterOrderByUltAlt(LocalDateTime.parse(filtroPadrao.getAlteracao()));
            segmentos.forEach(segmento -> segmento.setEmpresas(empresaRespository.findAllByCodSegmento(segmento.getId())));
            return ResponseEntity.ok().body(segmentos);
        }
        List<Segmento> allSegmentos = segmentoRepository.findAll();
        allSegmentos.forEach(segmento -> segmento.setEmpresas(empresaRespository.findAllByCodSegmento(segmento.getId())));
        return ResponseEntity.ok().body(allSegmentos);
    }
}

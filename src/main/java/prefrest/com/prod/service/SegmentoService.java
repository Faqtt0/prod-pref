package prefrest.com.prod.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prefrest.com.prod.event.RecursoEvent;
import prefrest.com.prod.model.empresas.Segmento;
import prefrest.com.prod.repository.EmpresaRespository;
import prefrest.com.prod.repository.SegmentoRepository;

import javax.servlet.http.HttpServletResponse;

@Service
public class SegmentoService {


    @Autowired
    SegmentoRepository segmentoRepository;

    @Autowired
    EmpresaRespository empresaRespository;

    public ResponseEntity<Segmento> atualizarSegmento(Long codigo, Segmento segmento) {
        Segmento segmentoSalvo = segmentoRepository.findOne(codigo);
        BeanUtils.copyProperties(segmento, segmentoSalvo, "id");
        return ResponseEntity.ok(segmentoRepository.save(segmentoSalvo));
    }

    public ResponseEntity<Segmento> criarSegmento(Segmento segmento, ApplicationEventPublisher publisher, HttpServletResponse response) {
        Segmento segmentoSalvo = segmentoRepository.save(segmento);
        publisher.publishEvent(new RecursoEvent(this, response, segmentoSalvo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(segmentoSalvo);
    }

    public void deletarSegmentoEmpresa(Long codigo) {
        empresaRespository.deleteByCodSegmento(codigo);
        segmentoRepository.delete(codigo);
    }
}

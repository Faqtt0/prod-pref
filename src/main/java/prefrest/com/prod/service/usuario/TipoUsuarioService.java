package prefrest.com.prod.service.usuario;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prefrest.com.prod.event.RecursoEvent;
import prefrest.com.prod.model.TipoUsuario;
import prefrest.com.prod.repository.usuario.TipoUsuarioRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class TipoUsuarioService {
    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;

    public ResponseEntity<List<TipoUsuario>> getTipoUsuarios() {
        return ResponseEntity.ok().body(tipoUsuarioRepository.findAll());
    }

    public ResponseEntity<TipoUsuario> salvarTipoUsuario(TipoUsuario tipoUsuario, ApplicationEventPublisher publisher, HttpServletResponse response) {
        TipoUsuario tipoUsuarioSalvo = tipoUsuarioRepository.save(tipoUsuario);
        publisher.publishEvent(new RecursoEvent(this, response, tipoUsuarioSalvo.getId()));
        if (tipoUsuarioSalvo != null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoUsuarioSalvo);
    }

    public ResponseEntity atualizar(Long codigo, TipoUsuario tipoUsuario) {
        TipoUsuario tipoUsuarioSalvo = tipoUsuarioRepository.findOne(codigo);
        if (tipoUsuario != null){
            BeanUtils.copyProperties(tipoUsuario, tipoUsuarioSalvo, "ID");
            tipoUsuarioRepository.save(tipoUsuarioSalvo);
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity deletar(Long codigo) {
        TipoUsuario tipoSalvo = tipoUsuarioRepository.findOne(codigo);
        if (tipoSalvo != null){
            tipoUsuarioRepository.delete(codigo);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}

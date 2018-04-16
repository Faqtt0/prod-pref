package prefrest.com.prod.service.usuario;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prefrest.com.prod.event.RecursoEvent;
import prefrest.com.prod.model.Usuario;
import prefrest.com.prod.repository.usuario.UserRepository;
import prefrest.com.prod.util.UtilPasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<List<Usuario>> recuperaUsuarios() {
        return ResponseEntity.ok().body(userRepository.findAll());
    }

    public ResponseEntity<Usuario> salvarUsuario(Usuario usuario, ApplicationEventPublisher publisher, HttpServletResponse response) {
        usuario.setSenha(UtilPasswordEncoder.encodePassword(usuario.getSenha()));
        Usuario usuarioSalvo = userRepository.save(usuario);
        if (usuarioSalvo != null){
            publisher.publishEvent(new RecursoEvent(this, response, usuarioSalvo.getId()));
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity atualizarUsuario(Long codigo, Usuario usuario) {
        Usuario usuarioSalvo = userRepository.findOne(codigo);
        if (usuarioSalvo != null) {
            BeanUtils.copyProperties(usuario, usuarioSalvo, "ID");
            usuarioSalvo.setSenha(UtilPasswordEncoder.encodePassword(usuarioSalvo.getSenha()));
            userRepository.save(usuarioSalvo);
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity deleteUsuario(Long codigo) {
        Usuario usuarioSalvo = userRepository.findOne(codigo);
        if (usuarioSalvo != null) {
            userRepository.delete(codigo);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}

package prefrest.com.prod.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.TipoUsuario;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Long> {
}

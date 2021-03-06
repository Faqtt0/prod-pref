package prefrest.com.prod.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsuario(String usuario);
}

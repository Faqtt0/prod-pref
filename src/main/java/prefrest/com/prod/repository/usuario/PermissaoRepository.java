package prefrest.com.prod.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prefrest.com.prod.model.Permissao;

import java.util.List;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM PERMISSAO ORDER BY ID")
    List<Permissao> findAllOrderById();
}

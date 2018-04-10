package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.DeleteTables;

public interface DeleteTablesRepository extends JpaRepository<DeleteTables, Long> {

}

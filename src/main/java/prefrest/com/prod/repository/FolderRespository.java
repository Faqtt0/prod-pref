package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.Folder;

public interface FolderRespository extends JpaRepository<Folder, Long>{
}

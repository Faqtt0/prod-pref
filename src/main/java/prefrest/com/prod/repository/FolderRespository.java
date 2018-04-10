package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prefrest.com.prod.model.Folder;

import java.time.LocalDateTime;
import java.util.List;

public interface FolderRespository extends JpaRepository<Folder, Long> {

    List<Folder> findAllByUltAltAfterOrderByUltAlt(LocalDateTime dateTime);
}

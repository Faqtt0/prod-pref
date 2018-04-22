package prefrest.com.prod.repository;

import prefrest.com.prod.model.Folder;

import java.time.LocalDateTime;
import java.util.List;

public interface CommomRepository {
    Long recuperaCodImagem(Long codigo, Class<?> clazz);
    boolean updateCodImagem(Long codigo, Long codigoImagem, Class<?> clazz);
    boolean updateUltAlt(Class<?> clazz, LocalDateTime ultalt, Long id);

}

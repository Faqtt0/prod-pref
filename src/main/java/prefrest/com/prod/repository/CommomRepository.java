package prefrest.com.prod.repository;

import java.time.LocalDateTime;

public interface CommomRepository {
    Long recuperaCodImagem(Long codigo, Class<?> clazz);
    boolean updateCodImagem(Long codigo, Long codigoImagem, Class<?> clazz);
    boolean updateUltAlt(Class<?> clazz, LocalDateTime ultalt, Long id);
}

package prefrest.com.prod.repository;

public interface CommomRepository {
    Long recuperaCodImagem(Long codigo, Class<?> clazz);
    boolean updateCodImagem(Long codigo, Long codigoImagem, Class<?> clazz);
}

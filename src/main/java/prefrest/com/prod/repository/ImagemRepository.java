package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.Imagem;
import prefrest.com.prod.model.Imagens;

public interface ImagemRepository{
    Imagem findByDiretorio(String diretorio);
    Imagem findById(Long id);
    boolean updateImagem(Imagem imagem);
    Imagem insertImagem(String caminho);
    boolean deleteById(Long codigo);
}

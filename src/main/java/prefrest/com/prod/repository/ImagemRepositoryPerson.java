package prefrest.com.prod.repository;

import prefrest.com.prod.model.Imagens;
import prefrest.com.prod.repository.filter.ImagensFilter;

import java.util.List;

public interface ImagemRepositoryPerson {
    boolean updateImagem(String caminhoImagem, Imagens imagem);
    boolean updateImagemInfos(Imagens imagem);
    List<Imagens> getImagens (ImagensFilter filter);
}

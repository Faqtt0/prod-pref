package prefrest.com.prod.repository;

import prefrest.com.prod.model.Imagens;
import prefrest.com.prod.repository.filter.ImagensFilter;
import prefrest.com.prod.repository.impl.ImagemCommonRepositoryImpl;

import java.util.List;

public interface ImagemRepositoryPerson {
    boolean updateImagemInfos(Imagens imagem);
    List<Imagens> getImagens (ImagensFilter filter);
}

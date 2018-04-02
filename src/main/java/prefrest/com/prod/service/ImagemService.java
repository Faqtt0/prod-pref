package prefrest.com.prod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import prefrest.com.prod.event.RecursoEvent;
import prefrest.com.prod.model.Imagens;
import prefrest.com.prod.repository.ImagemRepository;
import prefrest.com.prod.util.UtilBase64Image;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class ImagemService {

    @Autowired
    ImagemRepository imagemRepository;

    public ResponseEntity<Imagens> salvarImagem(Imagens imagem, HttpServletResponse response, ApplicationEventPublisher publisher, MultipartFile file) throws IOException {
        imagem.setImagem(file.getBytes());
        Imagens imagemSalva = imagemRepository.save(imagem);
        publisher.publishEvent(new RecursoEvent(this, response, imagem.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(imagemSalva);
    }
}

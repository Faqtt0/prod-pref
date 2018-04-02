package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import prefrest.com.prod.model.Imagens;
import prefrest.com.prod.service.ImagemService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/imagens")
public class ImagemResource {

    @Autowired
    ImagemService service;

    @Autowired
    ApplicationEventPublisher publisher;

    @PostMapping()
    public ResponseEntity<Imagens> salvarImagem(@Valid @RequestBody Imagens imagem,
                                                @RequestPart("file") MultipartFile file,
                                                HttpServletResponse response) throws IOException {
        return service.salvarImagem(imagem, response, publisher, file);
    }
}

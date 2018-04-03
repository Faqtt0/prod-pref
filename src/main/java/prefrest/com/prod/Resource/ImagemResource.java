package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import prefrest.com.prod.model.Imagens;
import prefrest.com.prod.service.ImagemService;

import javax.persistence.Convert;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/imagens")
public class ImagemResource {

    @Autowired
    ImagemService service;

    @Autowired
    ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<Imagens> retornaImagens (){
        return null;
    }

    @PostMapping()
    public ResponseEntity<Imagens> salvarImagem(@Valid @RequestBody Imagens imagem,
                                                HttpServletResponse response) {
        return service.salvarImagem(imagem, response, publisher);
    }

    @PutMapping ("/{codigo}")
    public ResponseEntity atualizaImagemInfo (@PathVariable Long codigo, @RequestBody Imagens imagens) {
        return null;
    }

    @PutMapping("/{codigo}/imagem")
    public ResponseEntity atualizaImagem( @RequestParam MultipartFile file,
                                HttpServletResponse response) throws IOException {
        return  service.atualizarSalvarImagem(file);
    }


}

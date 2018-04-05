package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import prefrest.com.prod.model.Imagens;
import prefrest.com.prod.repository.filter.ImagensFilter;
import prefrest.com.prod.service.ImagemService;

import javax.persistence.Convert;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/imagens")
public class ImagemResource {

    @Autowired
    ImagemService service;

    @Autowired
    ApplicationEventPublisher publisher;

    @GetMapping()
    public ResponseEntity<List<Imagens>> retornaImagens(ImagensFilter filter) {
        return service.getImagens(filter);
    }

    @PostMapping()
    public ResponseEntity<Imagens> salvarImagem(@Valid @RequestBody Imagens imagem,
                                                HttpServletResponse response) {
        return service.salvarImagem(imagem, response, publisher);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity atualizaImagemInfo(@PathVariable Long codigo, @RequestBody Imagens imagens) {
        return service.atualizarImagemInfos(codigo, imagens);
    }

    @PutMapping("/{codigo}/imagem")
    public ResponseEntity atualizaImagem(@PathVariable Long codigo,
                                         @RequestParam MultipartFile file) throws IOException, InvocationTargetException, IllegalAccessException {
        return service.atualizarSalvarImagem(file, codigo);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletarImagem(@PathVariable Long codigo) {
        service.deletarImagem(codigo);
    }

}

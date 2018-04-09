package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import prefrest.com.prod.model.Folder;
import prefrest.com.prod.repository.filter.ImagensFilter;
import prefrest.com.prod.service.FolderService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.ImageFilter;
import java.util.List;

@RestController
@RequestMapping("/folder")
public class FolderResource {
    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    FolderService service;

    @GetMapping()
    public ResponseEntity<List<Folder>> retornaFolders(ImagensFilter filter){
        //TODO ajustar Folder get
        return null;
    }

    @PostMapping()
    public ResponseEntity<Folder> salvaFolder(@Valid @RequestBody Folder folder, HttpServletResponse response){
        return service.salvarFolder(folder, response, publisher) ;
    }

    @PutMapping("/{codigo}")
    public ResponseEntity atualizarIFolderInfos(@PathVariable Long codigo,
                                                @Valid @RequestBody Folder folder){
        return service.atualizarFolder(codigo, folder);
    }

    @PutMapping("/{codigo}/imagem/")
    public ResponseEntity atualizarSalvarImagem(@PathVariable Long codigo,
                                                @RequestPart MultipartFile file){
        return service.atualizarSalvarImagem(codigo, file);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity deletarImagem(@PathVariable Long codigo){
        //TODO DELETAR FOLDER  E IMAGEM
        return null;
    }

}

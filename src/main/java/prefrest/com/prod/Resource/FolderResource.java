package prefrest.com.prod.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import prefrest.com.prod.model.Folder;

import javax.validation.Valid;
import java.awt.image.ImageFilter;
import java.util.List;

@RestController
@RequestMapping("/folder")
public class FolderResource {

    @GetMapping()
    public ResponseEntity<List<Folder>> retornaFolders(ImageFilter filter){
        //TODO ajustar Folder get
        return null;
    }

    @PostMapping()
    public ResponseEntity<Folder> salvaFolder(@Valid @RequestBody Folder folder){
        //TODO ajustar-salvar Folder
        return null;
    }

    @PutMapping("/{codigo}")
    public ResponseEntity atualizarIFolderInfos(@PathVariable Long codigo,
                                                @Valid @RequestBody Folder folder){
        //TODO Atualizar FOLD
        return null;
    }

    @PutMapping("/{codigo}/imagem/")
    public ResponseEntity atualizarSalvarImagem(@PathVariable Long codigo,
                                                @RequestPart MultipartFile file){
        //TODO criar salvar imagem em FOLDER ---- VERIFICAR SE TEM IMAGEM COM O MESMO NOME E SUBSTITUIR
        return null;
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity deletarImagem(@PathVariable Long codigo){
        //TODO DELETAR FOLDER  E IMAGEM
        return null;
    }

}

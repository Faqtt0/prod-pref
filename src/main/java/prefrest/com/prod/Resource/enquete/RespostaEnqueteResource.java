package prefrest.com.prod.Resource.enquete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.model.enquetes.RespostaEnquete;
import prefrest.com.prod.repository.filter.FiltroRespostaEnquete;
import prefrest.com.prod.service.RespostaEnqueteService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/enqueteresponder")
public class RespostaEnqueteResource {

    @Autowired
    RespostaEnqueteService respostaEnqueteService;

    @GetMapping("/cpf")
    public ResponseEntity verificaCpf (FiltroRespostaEnquete filtro) {
        return respostaEnqueteService.isRespostaEnquete(filtro);
    }

    @PostMapping()
    public ResponseEntity salvarListaEnquete(@Valid @RequestBody List<RespostaEnquete> respostas) {
        return respostaEnqueteService.salvarRespostasEnquete(respostas);
    }
}

package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.model.enquetes.Enquetes;
import prefrest.com.prod.repository.EnquetePersonRepository;
import prefrest.com.prod.repository.filter.EnqueteFilter;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/enquetes")
public class EnqueteResource {

    @Autowired
    EnquetePersonRepository repository;

    /*@GetMapping
    public List<Enquetes> retornaEnquetes(@RequestParam("id") Integer id,
                                          @RequestParam("descricao") String descricao,
                                          @RequestParam("ativo") boolean ativo) {
        return repository.filtrarEnquetes(id, descricao, ativo);
    }*/

    @GetMapping()
    public List<Enquetes> retornaEnquetes(EnqueteFilter filtro) {
        return repository.filtrarEnquetes(filtro);
    }

    @PostMapping()
    public void salvarEnquete(@Valid @RequestBody Enquetes enquetes){

    }
}

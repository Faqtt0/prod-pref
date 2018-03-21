package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prefrest.com.prod.model.enquetes.Enquetes;
import prefrest.com.prod.repository.EnqueteRepository;

import java.util.List;

@RestController
@RequestMapping("/enquetes")
public class EnqueteResource {

    @Autowired
    EnqueteRepository enqueteRepository;

    @GetMapping
    public List<Enquetes> retornaEnquetes() {
        return enqueteRepository.findByEnquetesLimit();
    }
}

package prefrest.com.prod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prefrest.com.prod.repository.EnquetePersonRepository;

@Service
public class EnqueteService {

    @Autowired
    EnquetePersonRepository repository;

}

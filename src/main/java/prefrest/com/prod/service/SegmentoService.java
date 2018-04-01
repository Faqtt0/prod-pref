package prefrest.com.prod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prefrest.com.prod.repository.SegmentoRepository;

@Service
public class SegmentoService {

    @Autowired
    SegmentoRepository segmentoRepository;
}

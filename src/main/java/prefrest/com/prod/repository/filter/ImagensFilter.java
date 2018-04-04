package prefrest.com.prod.repository.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImagensFilter {
    private String alteracao;
    private boolean todas;
}

package prefrest.com.prod.model.enquetes;

import lombok.Data;

import java.io.Serializable;

@Data
public class PerguntaID implements Serializable {
    private Long codigo;
    private Enquete enquete;
}


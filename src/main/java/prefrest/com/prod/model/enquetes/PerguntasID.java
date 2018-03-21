package prefrest.com.prod.model.enquetes;

import lombok.Data;

import java.io.Serializable;

@Data
public class PerguntasID  implements Serializable{
    private Long codigo;
    private Enquetes enquetes;
}


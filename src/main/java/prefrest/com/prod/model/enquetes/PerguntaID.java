package prefrest.com.prod.model.enquetes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerguntaID implements Serializable {
    private Long codigo;
    private Long codEnquete;
}


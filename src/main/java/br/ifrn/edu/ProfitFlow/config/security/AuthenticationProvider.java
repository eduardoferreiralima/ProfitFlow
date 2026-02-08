package br.ifrn.edu.ProfitFlow.config.security;

import br.ifrn.edu.ProfitFlow.models.Usuario;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider {

    public Usuario getUsuarioLogado() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return (Usuario) authentication.getPrincipal();
    }

    public Long getUsuarioId() {
        Usuario usuario = getUsuarioLogado();
        return (usuario != null) ? usuario.getId() : null;
    }
}

package br.ifrn.edu.ProfitFlow.dto;

import br.ifrn.edu.ProfitFlow.models.Usuario;

public record ProfileDTO(
        Long id,
        String nome,
        String email,
        String role,
        String telefone
) {
    public ProfileDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(),
                usuario.getRole().name(), usuario.getTelefone());
    }
}
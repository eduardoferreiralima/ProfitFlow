package br.ifrn.edu.ProfitFlow.config.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Auth", description = "Operações relacionadas à autenticação do usuário")
public interface AuthControllerDocs {

    @Operation(summary = "Realiza login", description = "Autentica o usuário e gera um token JWT.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    ResponseEntity<?> login(
            @Parameter(description = "Email do usuário", example = "usuario@exemplo.com") String email,
            @Parameter(description = "Senha do usuário", example = "senha123") String senha
    );

    @Operation(summary = "Registra um novo usuário", description = "Cria um novo usuário no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Usuário já existe")
    })
    ResponseEntity<?> register(
            @Parameter(description = "Email do usuário", example = "usuario@exemplo.com") String email,
            @Parameter(description = "Senha do usuário", example = "senha123") String senha
    );

    @Operation(summary = "Retorna dados do usuário autenticado", description = "Obtém as informações do perfil do usuário logado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil retornado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    ResponseEntity<?> getProfile();

    @Operation(summary = "Logout do usuário", description = "Invalida o token JWT atual.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso")
    })
    ResponseEntity<?> logout();
}

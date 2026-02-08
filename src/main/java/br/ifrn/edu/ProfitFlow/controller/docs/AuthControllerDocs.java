package br.ifrn.edu.ProfitFlow.controller.docs;

import br.ifrn.edu.ProfitFlow.dto.AuthDataDTO;
import br.ifrn.edu.ProfitFlow.models.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "Operações relacionadas à autenticação do usuário")
public interface AuthControllerDocs {

    @Operation(summary = "Realiza login", description = "Autentica o usuário e gera um token JWT.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    ResponseEntity<?> login(
            AuthDataDTO dados
    );

    @Operation(summary = "Retorna dados do usuário autenticado", description = "Obtém as informações do perfil do usuário logado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil retornado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    ResponseEntity<?> getProfile(Usuario logado);

    @Operation(summary = "Logout do usuário", description = "Invalida o token JWT atual.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso")
    })
    ResponseEntity<?> logout();

}

package br.ifrn.edu.ProfitFlow.config.docs;

import br.ifrn.edu.ProfitFlow.dto.request.RequestPessoaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponsePessoaDTO;
import br.ifrn.edu.ProfitFlow.models.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Tag(name = "Pessoas", description = "Operações relacionadas a Clientes e Fornecedores")
public interface PessoaControllerDocs {

    @Operation(summary = "Lista todas as pessoas", description = "Retorna uma lista de todos os clientes e fornecedores cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    ResponseEntity<List<Usuario>> findAll();

    @Operation(summary = "Retorna uma pessoa específica", description = "Busca uma pessoa pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    ResponseEntity<Optional<Usuario>> findById(
            @Parameter(description = "ID da pessoa", example = "1") Integer id);

    @Operation(summary = "Cadastra uma nova pessoa", description = "Cria um novo cliente ou fornecedor no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<ResponsePessoaDTO> save(
            @Parameter(description = "Dados da pessoa a ser cadastrada") RequestPessoaDTO request) throws Exception;

    @Operation(summary = "Atualiza uma pessoa", description = "Atualiza os dados de uma pessoa existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    ResponseEntity<ResponsePessoaDTO> update(
            @Parameter(description = "ID da pessoa a ser atualizada", example = "1") Integer id,
            @Parameter(description = "Novos dados da pessoa") RequestPessoaDTO request);

    @Operation(summary = "Remove uma pessoa", description = "Deleta uma pessoa do sistema pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pessoa removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    ResponseEntity<ResponsePessoaDTO> delete(
            @Parameter(description = "ID da pessoa a ser deletada", example = "1") Integer id);

    @Operation(summary = "Busca pessoas por nome, CPF ou CNPJ", description = "Permite filtrar pessoas pelo nome ou documentos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultados encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhuma pessoa encontrada")
    })
    ResponseEntity<List<Usuario>> findByNome(
            @Parameter(description = "Nome, CPF ou CNPJ a ser buscado", example = "João") String nome);

    @Operation(summary = "Lista pessoas por tipo", description = "Retorna todas as pessoas de um tipo específico (CLIENTE ou FORNECEDOR).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista filtrada por tipo retornada")
    })
    ResponseEntity<List<Usuario>> findByTipo(
            @Parameter(description = "Tipo da pessoa (CLIENTE ou FORNECEDOR)", example = "CLIENTE") String tipo);
}

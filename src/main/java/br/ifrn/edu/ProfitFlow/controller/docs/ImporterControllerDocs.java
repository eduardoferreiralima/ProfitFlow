package br.ifrn.edu.ProfitFlow.controller.docs;

import br.ifrn.edu.ProfitFlow.dto.ImporterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Tag(name = "Importar Dados", description = "Operações relacionadas à importação de arquivos e consulta de histórico")
public interface ImporterControllerDocs {

    @Operation(
            summary = "Obter template de importação",
            description = "Retorna o arquivo template que deve ser usado como base para importar dados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Template retornado com sucesso")
    })
    ResponseEntity<?> getTemplate();


    @Operation(
            summary = "Listar importações",
            description = "Retorna o histórico de todas as importações realizadas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de importações retornada com sucesso")
    })
    ResponseEntity<?> getImports();


    @Operation(
            summary = "Buscar importação por ID",
            description = "Retorna os detalhes de uma importação específica."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Importação encontrada"),
            @ApiResponse(responseCode = "404", description = "Importação não encontrada")
    })
    ResponseEntity<?> getImports(
            @Parameter(description = "ID da importação", example = "123") String id
    );


    @Operation(
            summary = "Fazer upload de arquivo",
            description = "Recebe um arquivo via multipart/form-data e realiza o processamento de importação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Arquivo importado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ImporterDTO.class))),
            @ApiResponse(responseCode = "400", description = "Arquivo inválido")
    })
    ResponseEntity<List<ImporterDTO>> uploadFile(
            @Parameter(
                    description = "Arquivo a ser enviado",
                    required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            MultipartFile file
    );


    @Operation(
            summary = "Excluir importação",
            description = "Remove uma importação do histórico pelo ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Importação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Importação não encontrada")
    })
    ResponseEntity<?> deleteFile(
            @Parameter(description = "ID da importação", example = "123") String id
    );



}

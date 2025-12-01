package br.ifrn.edu.ProfitFlow.dto.response;

import br.ifrn.edu.ProfitFlow.models.enums.UsuarioRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter @Setter
@Schema(description = "Objeto de resposta com os dados completos de uma pessoa cadastrada no sistema")
public class ResponsePessoaDTO {

    @Schema(description = "Identificador único da pessoa",
            example = "10")
    private Long id;

    @Schema(description = "Nome completo da pessoa",
            example = "João da Silva")
    private String nome;

    @Schema(description = "Endereço de e-mail utilizado para login e comunicação",
            example = "joao.silva@email.com")
    private String email;

    @Schema(description = "CPF ou CNPJ da pessoa, com ou sem formatação",
            example = "123.456.789-00")
    private String cpfCnpj;

    @Schema(description = "Telefone principal para contato, incluindo DDD",
            example = "(84) 98822-3344")
    private String telefone;

    @Schema(description = "Endereço residencial ou comercial da pessoa",
            example = "Av. Sen. Salgado Filho, 1000 - Tirol, Natal/RN")
    private String endereco;

    @Schema(description = "Papel ou tipo de acesso do usuário no sistema",
            example = "ADMIN")
    private UsuarioRole role;

    @Schema(description = "Indica se o usuário está ativo no sistema",
            example = "true")
    private boolean ativo;

    @Schema(description = "Data e hora em que o cadastro foi criado",
            example = "2025-01-15T14:22:00")
    private LocalDateTime dataCadastro;

    @Schema(description = "Data e hora da última atualização cadastral",
            example = "2025-02-10T09:47:30")
    private LocalDateTime dataAtualizacao;
}


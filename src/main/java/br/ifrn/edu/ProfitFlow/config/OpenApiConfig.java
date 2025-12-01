package br.ifrn.edu.ProfitFlow.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Profit Flow - Sistema de Gestão Financeira Empresarial")
                        .version("1.0.0")
                        .description("""
                                    O Sistema de Gestão Financeira é uma API RESTful corporativa desenvolvida 
                                    em Java + Spring Boot, com autenticação via Spring Security, projetada para 
                                    controle de Receitas, Despesas, Clientes, Fornecedores e Contas Financeiras 
                                    (a pagar e a receber).
                            """)
                        .contact(new Contact()
                                .name("Eduardo Lima")
                                .email("ferreira.lima1@escolar.ifrn.edu.br")
                                .url("https://github.com/eduardoferreiralima"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT"))
                )
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor Local de Desenvolvimento")
                ))
                .tags(List.of(
                        new Tag().name("Auth").description("Operações relacionadas a Autenticação"),
                        new Tag().name("Administrar Usuários").description("Operações relacionadas a Administtração de Usuários"),
                        new Tag().name("Registro Financeiro").description("Operações relacionadas a Registros Financeiros (Receitas e Despesas)"),
                        new Tag().name("Importar Dados").description("Operações relacionadas a Importação de Dados Financeiros"),
                        new Tag().name("Relatorios").description("Operações relacionadas a Relatorios")
                ))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação do Projeto")
                        .url("https://github.com/eduardoferreiralima/ProfitFlow")

                );
    }

    @RestControllerAdvice // Esta anotação habilita o tratamento global de exceções
    public static class ValidationExceptionHandler {

        @ResponseStatus(HttpStatus.BAD_REQUEST) // Garante que o status 400 seja retornado
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return errors;
        }
    }
}


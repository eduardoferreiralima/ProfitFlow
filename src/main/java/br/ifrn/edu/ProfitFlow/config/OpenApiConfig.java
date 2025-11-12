package br.ifrn.edu.ProfitFlow.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
                        new Tag().name("Pessoas").description("Operações relacionadas a Clientes e Fornecedores"),
                        new Tag().name("Contas").description("Operações relacionadas a Contas a pagar e a receber"),
                        new Tag().name("Financeiro").description("Operações relacionadas a Receitas e Despesas"),
                        new Tag().name("Relatorios").description("Operações relacionadas a Relatorios")
                ))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação do Projeto")
                        .url("https://github.com/eduardoferreiralima/ProfitFlow")

                );
    }
}


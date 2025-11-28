package br.ifrn.edu.ProfitFlow.mapper;

import br.ifrn.edu.ProfitFlow.dto.request.RequestReceitaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponseReceitaDTO;
import br.ifrn.edu.ProfitFlow.models.Receita;
import br.ifrn.edu.ProfitFlow.models.enums.ReceitaStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface MapperDespesa {

    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "dataPagamento", target = "dataPagamento")
    @Mapping(source = "dataVencimento", target = "dataVencimento")
    @Mapping(source = "fornecedorId", target = "fonecedor")
    Receita mapRequestDespesaDTOtoDespesa(RequestReceitaDTO receita);

    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "dataPagamento", target = "dataPagamento")
    @Mapping(source = "dataVencimento", target = "dataVencimento")
    @Mapping(source = "fornecedor", target = "fornecedorId")
    RequestReceitaDTO mapDespesaToRequestDespesaDTO(Receita receita);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "dataPagamento", target = "dataPagamento")
    @Mapping(source = "dataVencimento", target = "dataVencimento")
    @Mapping(source = "fornecedor", target = "fornecedor")
    ResponseReceitaDTO mapDespesaToResponseDespesaDTO(Receita receita);
}

package br.ifrn.edu.ProfitFlow.mapper;

import br.ifrn.edu.ProfitFlow.dto.request.RequestContaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponseContaDTO;
import br.ifrn.edu.ProfitFlow.models.Contas;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapperConta {

    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "dataVencimento", target = "dataVencimento")
    @Mapping(source = "dataPagamento", target = "dataPagamento")
    @Mapping(source = "tipo", target = "tipo")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "clienteFornecedorId", target = "clienteFornecedor")
    Contas mapContaDtoToContas(RequestContaDTO c);

    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "dataVencimento", target = "dataVencimento")
    @Mapping(source = "dataPagamento", target = "dataPagamento")
    @Mapping(source = "tipo", target = "tipo")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "clienteFornecedor", target = "clienteFornecedorId")
    RequestContaDTO mapContasToRequestContaDTO(Contas c);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "dataVencimento", target = "dataVencimento")
    @Mapping(source = "dataPagamento", target = "dataPagamento")
    @Mapping(source = "tipo", target = "tipo")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "clienteFornecedor", target = "clienteFornecedorId")
    ResponseContaDTO mapContasToResponseContaDTO(Contas c);
}

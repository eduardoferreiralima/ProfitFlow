package br.ifrn.edu.ProfitFlow.mapper;

import br.ifrn.edu.ProfitFlow.dto.ImporterDTO;
import br.ifrn.edu.ProfitFlow.dto.request.RequestRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponseRegistroFinanceiroDTO;
import br.ifrn.edu.ProfitFlow.models.RegistroFinanceiro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapperRegistroFinanceiro {

    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "tipo", target = "tipo")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "dataPrevista", target = "dataPrevista")
    @Mapping(source = "dataPagamento", target = "dataPagamento")
    RegistroFinanceiro mapRegistroFinanceiroDtoToRegistroFinanceiro(RequestRegistroFinanceiroDTO c);

    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "tipo", target = "tipo")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "dataPrevista", target = "dataPrevista")
    @Mapping(source = "dataPagamento", target = "dataPagamento")
    RequestRegistroFinanceiroDTO mapRegistroFinanceiroToRequestRegistroFinanceiroDTO(RegistroFinanceiro c);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "tipo", target = "tipo")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "dataPrevista", target = "dataPrevista")
    @Mapping(source = "dataPagamento", target = "dataPagamento")
    ResponseRegistroFinanceiroDTO mapRegistroFinanceiroToResponseRegistroFinanceiroDTO(RegistroFinanceiro c);


    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "tipoLancamento", target = "tipo")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "formaPagamento", target = "formaPagamento")
    @Mapping(source = "dataPrevista", target = "dataPrevista")
    @Mapping(source = "dataPagamento", target = "dataPagamento")
    RequestRegistroFinanceiroDTO mapImporterToRequestRegistroFinanceiroDTO(ImporterDTO dto);

    List<ResponseRegistroFinanceiroDTO> toResponseRegistroFinanceiroDTOList(List<RegistroFinanceiro> registroFinanceiros);

    void updateRegistroFinanceiroFromDTO(RequestRegistroFinanceiroDTO dto, @MappingTarget RegistroFinanceiro c);
}

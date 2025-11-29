package br.ifrn.edu.ProfitFlow.mapper;

import br.ifrn.edu.ProfitFlow.dto.request.RequestContaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponseContaDTO;
import br.ifrn.edu.ProfitFlow.models.Conta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapperConta {

    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "tipo", target = "tipo")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "dataPrevista", target = "dataPrevista")
    @Mapping(source = "dataPagamento", target = "dataPagamento")
    Conta mapContaDtoToConta(RequestContaDTO c);

    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "tipo", target = "tipo")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "dataPrevista", target = "dataPrevista")
    @Mapping(source = "dataPagamento", target = "dataPagamento")
    RequestContaDTO mapContaToRequestContaDTO(Conta c);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "tipo", target = "tipo")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "dataPrevista", target = "dataPrevista")
    @Mapping(source = "dataPagamento", target = "dataPagamento")
    ResponseContaDTO mapContaToResponseContaDTO(Conta c);

    List<ResponseContaDTO> toResponseContaDTOList(List<Conta> contas);

    void updateContaFromDTO(RequestContaDTO dto, @MappingTarget Conta c);
}

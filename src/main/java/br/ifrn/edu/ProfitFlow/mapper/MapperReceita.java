package br.ifrn.edu.ProfitFlow.mapper;

import br.ifrn.edu.ProfitFlow.dto.request.RequestReceitaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponseReceitaDTO;
import br.ifrn.edu.ProfitFlow.models.Receita;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapperReceita {

    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "dataRecebimento", target = "dataRecebimento")
    @Mapping(source = "dataPrevista", target = "dataPrevista")
    @Mapping(source = "clientId", target = "cliente")
    Receita mapRequestReceitaDTOtoReceita(RequestReceitaDTO receita);

    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "dataRecebimento", target = "dataRecebimento")
    @Mapping(source = "dataPrevista", target = "dataPrevista")
    @Mapping(source = "cliente", target = "clientId")
    RequestReceitaDTO mapReceitaToRequestReceitaDTO(Receita receita);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "dataRecebimento", target = "dataRecebimento")
    @Mapping(source = "dataPrevista", target = "dataPrevista")
    @Mapping(source = "cliente", target = "clientId")
    ResponseReceitaDTO mapReceitaToResponseReceitaDTO(Receita receita);
}

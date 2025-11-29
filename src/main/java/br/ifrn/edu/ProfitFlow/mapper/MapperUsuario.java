package br.ifrn.edu.ProfitFlow.mapper;

import br.ifrn.edu.ProfitFlow.dto.request.RequestPessoaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponsePessoaDTO;
import br.ifrn.edu.ProfitFlow.models.PessoaFisica;
import br.ifrn.edu.ProfitFlow.models.PessoaJuridica;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapperUsuario {
    @Mapping(target = "cpf", source = "cpfCnpj")
    PessoaFisica toEntityPessoaFisica(RequestPessoaDTO dto);

    @Mapping(target = "cnpj", source = "cpfCnpj")
    PessoaJuridica toEntityPessoaJuridica(RequestPessoaDTO dto);

    @Mapping(target = "cpfCnpj", source = "cpf")
    ResponsePessoaDTO mapPFtoResponsePessoaDTO(PessoaFisica pf);

    @Mapping(target = "cpfCnpj", source = "cnpj")
    ResponsePessoaDTO mapPJtoResponsePessoaDTO(PessoaJuridica pf);
}

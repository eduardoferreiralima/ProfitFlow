package br.ifrn.edu.ProfitFlow.mapper;

import br.ifrn.edu.ProfitFlow.dto.ImporterDTO;
import br.ifrn.edu.ProfitFlow.dto.request.RequestPessoaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponsePessoaDTO;
import br.ifrn.edu.ProfitFlow.models.PessoaFisica;
import br.ifrn.edu.ProfitFlow.models.PessoaJuridica;
import br.ifrn.edu.ProfitFlow.models.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MapperUsuario {
    @Mapping(target = "cpf", source = "cpfCnpj")
    PessoaFisica toEntityPessoaFisica(RequestPessoaDTO dto);

    @Mapping(target = "cnpj", source = "cpfCnpj")
    PessoaJuridica toEntityPessoaJuridica(RequestPessoaDTO dto);

    @Mapping(target = "cpf", source = "cpfCnpj")
    PessoaFisica toEntityPessoaFisica(ResponsePessoaDTO dto);

    @Mapping(target = "cnpj", source = "cpfCnpj")
    PessoaJuridica toEntityPessoaJuridica(ResponsePessoaDTO dto);


    @Mapping(target = "cpfCnpj", source = "cpf")
    ResponsePessoaDTO mapPFtoResponsePessoaDTO(PessoaFisica pf);

    @Mapping(target = "cpfCnpj", source = "cpfCnpj")
    @Mapping(target = "nome", source = "nomeClienteFornecedor")
    RequestPessoaDTO mapimportertoResponsePessoaDTO(ImporterDTO importerDTO);

    @Mapping(target = "cpfCnpj", source = "cnpj")
    ResponsePessoaDTO mapPJtoResponsePessoaDTO(PessoaJuridica pf);


    ResponsePessoaDTO mapUsuariotoResponsePessoaDTO(Usuario pf);

    // Atualiza uma entidade existente sem perder o ID e campos críticos
    void updatePessoaJuridicaFromDTO(RequestPessoaDTO dto, @MappingTarget PessoaJuridica pj);

    // Atualiza uma entidade existente sem perder o ID e campos críticos
    void updatePessoaFisicaFromDTO(RequestPessoaDTO dto, @MappingTarget PessoaFisica pf);
}

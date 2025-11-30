package br.ifrn.edu.ProfitFlow.services;

import br.ifrn.edu.ProfitFlow.dto.request.RequestPessoaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponsePessoaDTO;
import br.ifrn.edu.ProfitFlow.mapper.MapperUsuario;
import br.ifrn.edu.ProfitFlow.models.PessoaFisica;
import br.ifrn.edu.ProfitFlow.models.PessoaJuridica;
import br.ifrn.edu.ProfitFlow.models.Usuario;
import br.ifrn.edu.ProfitFlow.models.enums.PessoaTipo;
import br.ifrn.edu.ProfitFlow.repository.PessoaFisicaRepository;
import br.ifrn.edu.ProfitFlow.repository.PessoaJuridicaRepository;
import br.ifrn.edu.ProfitFlow.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private MapperUsuario mapper;

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id){
        return usuarioRepository.findById(id);
    }

    public ResponsePessoaDTO createUser(RequestPessoaDTO pessoa) throws Exception {

        if (pessoa.getPessoa() != PessoaTipo.CLIENTE && pessoa.getPessoa() != PessoaTipo.FORNECEDOR) {
            throw new IllegalArgumentException("Tipo de Pessoa Inválido! Use CLIENTE ou FORNECEDOR");
        }

        ResponsePessoaDTO responsePessoaDTO = new ResponsePessoaDTO();

        if (isCnpj(pessoa.getCpfCnpj())){

            PessoaJuridica pj = mapper.toEntityPessoaJuridica(pessoa);
            pj = pessoaJuridicaRepository.save(pj);
            responsePessoaDTO = mapper.mapPJtoResponsePessoaDTO(pj);

        } else if (isCpf(pessoa.getCpfCnpj())) {

            PessoaFisica pf = mapper.toEntityPessoaFisica(pessoa);
            pf = pessoaFisicaRepository.save(pf);
            responsePessoaDTO = mapper.mapPFtoResponsePessoaDTO(pf);

        }else {
            throw new IllegalArgumentException("CPF ou CNPJ Inválido!");
        }

        return responsePessoaDTO;
    }

    public boolean isCpf(String value) {
        String digits = value.replaceAll("\\D", "");
        return digits.length() == 11;
    }

    public boolean isCnpj(String value) {
        String digits = value.replaceAll("\\D", "");
        return digits.length() == 14;
    }

    public static String normalize(String value) {
        return value.replaceAll("\\D", "");
    }
}

package br.ifrn.edu.ProfitFlow.services;


import br.ifrn.edu.ProfitFlow.dto.request.RequestPessoaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponsePessoaDTO;
import br.ifrn.edu.ProfitFlow.models.ClienteFornecedor;
import br.ifrn.edu.ProfitFlow.models.Usuario;
import br.ifrn.edu.ProfitFlow.models.enums.PessoaTipo;
import br.ifrn.edu.ProfitFlow.models.enums.UsuarioRole;
import br.ifrn.edu.ProfitFlow.repository.ClienteFornecedorRepository;
import br.ifrn.edu.ProfitFlow.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteFornecedorService {

    @Autowired
    private ClienteFornecedorRepository clienteFornecedorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<ClienteFornecedor> findAll(){
        return clienteFornecedorRepository.findAll();
    }

    public Optional<ClienteFornecedor> findById(Integer id){
        return clienteFornecedorRepository.findById(id);
    }

    @Transactional
    public ResponsePessoaDTO salvar(RequestPessoaDTO pessoa) throws Exception {

        if (pessoa.getPessoa() != PessoaTipo.CLIENTE && pessoa.getPessoa() != PessoaTipo.FORNECEDOR) {
            throw new IllegalArgumentException("Tipo de Pessoa Inválido! Use CLIENTE ou FORNECEDOR");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(pessoa.getNome());
        usuario.setEmail(pessoa.getEmail());
        usuario.setAtivo(true);
        usuario.setRole(UsuarioRole.USUARIO);
        usuario.setDataCadastro(LocalDateTime.now());
        usuario.setDataAtualizacao(LocalDateTime.now());
        Usuario user = usuarioRepository.save(usuario);

        ClienteFornecedor clienteFornecedor = new ClienteFornecedor();
        clienteFornecedor.setUsuario(user);
        clienteFornecedor.setEndereco(pessoa.getEndereco());
        clienteFornecedor.setTelefone(pessoa.getTelefone());
        clienteFornecedor.setCpfCnpj(pessoa.getCpfCnpj());
        clienteFornecedor.setPessoa(pessoa.getPessoa());
        ClienteFornecedor clienteFornecedor1= clienteFornecedorRepository.save(clienteFornecedor);

        ResponsePessoaDTO responsePessoaDTO = new ResponsePessoaDTO();
        responsePessoaDTO.setId(clienteFornecedor1.getId());
        responsePessoaDTO.setNome(clienteFornecedor1.getUsuario().getNome());
        responsePessoaDTO.setEmail(clienteFornecedor1.getUsuario().getEmail());
        responsePessoaDTO.setEndereco(clienteFornecedor1.getEndereco());
        responsePessoaDTO.setTelefone(clienteFornecedor1.getTelefone());
        responsePessoaDTO.setCpfCnpj(clienteFornecedor1.getCpfCnpj());
        responsePessoaDTO.setPessoa(clienteFornecedor1.getPessoa());
        responsePessoaDTO.setDataCadastro(LocalDateTime.now());
        responsePessoaDTO.setDataAtualizacao(LocalDateTime.now());
        responsePessoaDTO.setRole(clienteFornecedor1.getUsuario().getRole());
        responsePessoaDTO.setAtivo(clienteFornecedor1.getUsuario().isAtivo());

        return responsePessoaDTO;
    }
}

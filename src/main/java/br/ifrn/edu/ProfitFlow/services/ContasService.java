package br.ifrn.edu.ProfitFlow.services;


import br.ifrn.edu.ProfitFlow.dto.request.RequestContaDTO;
import br.ifrn.edu.ProfitFlow.dto.response.ResponseContaDTO;
import br.ifrn.edu.ProfitFlow.models.ClienteFornecedor;
import br.ifrn.edu.ProfitFlow.models.Contas;
import br.ifrn.edu.ProfitFlow.models.enums.ContaStatus;
import br.ifrn.edu.ProfitFlow.repository.ClienteFornecedorRepository;
import br.ifrn.edu.ProfitFlow.repository.ContasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ContasService {

    @Autowired
    private ContasRepository contasRepository;

    @Autowired
    private ClienteFornecedorRepository clienteFornecedorRepository;


    public List<ResponseContaDTO> getContas(){
        List<ResponseContaDTO> contas = new ArrayList<>();

        List<Contas> c = contasRepository.findAll();
        for (Contas cc : c) {
            ResponseContaDTO conta = new ResponseContaDTO();
            conta.setId(cc.getId());
            conta.setDescricao(cc.getDescricao());
            conta.setValor(cc.getValor());
            conta.setDataPagamento(cc.getDataPagamento());
            conta.setDataVencimento(cc.getDataVencimento());
            conta.setDataAtualizacao(cc.getDataAtualizacao());
            conta.setTipo(cc.getTipo());
            conta.setStatus(cc.getStatus());
            conta.setClienteFornecedor(cc.getClienteFornecedor());
            contas.add(conta);
        }

        return contas;
    }


    public ResponseContaDTO getConta(Integer id) throws Exception {
        Contas c = contasRepository.findById(id)
                .orElseThrow(()-> new Exception("Conta não encontrada"));

        ResponseContaDTO conta = new ResponseContaDTO();
        conta.setId(c.getId());
        conta.setDescricao(c.getDescricao());
        conta.setValor(c.getValor());
        conta.setDataPagamento(c.getDataPagamento());
        conta.setDataVencimento(c.getDataVencimento());
        conta.setDataAtualizacao(c.getDataAtualizacao());
        conta.setTipo(c.getTipo());
        conta.setStatus(c.getStatus());
        conta.setClienteFornecedor(c.getClienteFornecedor());
        conta.setDataCriacao(c.getDataCriacao());

        return conta;
    }


    public ResponseContaDTO createConta(RequestContaDTO conta) throws Exception {
        Contas c = new Contas();
        c.setDescricao(conta.getDescricao());
        c.setValor(conta.getValor());
        c.setDataPagamento(conta.getDataPagamento());
        c.setDataVencimento(conta.getDataVencimento());
        c.setDataAtualizacao(LocalDateTime.now());
        c.setTipo(conta.getTipo());
        c.setStatus(conta.getStatus());
        c.setDataCriacao(LocalDateTime.now());
        ClienteFornecedor cf = clienteFornecedorRepository.findById(conta.getClienteFornecedorId())
                .orElseThrow(() -> new Exception(
                        "Cliente ou Fornecedor não encontrado com ID: " + conta.getClienteFornecedorId()
                ));
        c.setClienteFornecedor(cf);

        c = contasRepository.save(c);

        ResponseContaDTO contaResponse = new ResponseContaDTO();
        contaResponse.setId(c.getId());
        contaResponse.setDescricao(c.getDescricao());
        contaResponse.setValor(c.getValor());
        contaResponse.setDataPagamento(c.getDataPagamento());
        contaResponse.setDataVencimento(c.getDataVencimento());
        contaResponse.setDataAtualizacao(c.getDataAtualizacao());
        contaResponse.setTipo(c.getTipo());
        contaResponse.setStatus(c.getStatus());
        contaResponse.setClienteFornecedor(c.getClienteFornecedor());
        contaResponse.setDataCriacao(c.getDataCriacao());
        return contaResponse;
    }


    public ResponseContaDTO updateConta(Integer id, RequestContaDTO conta) throws Exception {
        Contas c = contasRepository.findById(id)
                .orElseThrow(() -> new Exception("Conta não encontrada com ID: " + id));
        c.setDescricao(conta.getDescricao());
        c.setValor(conta.getValor());
        c.setDataPagamento(conta.getDataPagamento());
        c.setDataVencimento(conta.getDataVencimento());
        c.setDataAtualizacao(LocalDateTime.now());
        c.setTipo(conta.getTipo());
        c.setStatus(conta.getStatus());
        contasRepository.save(c);

        ResponseContaDTO contaResponse = new ResponseContaDTO();
        contaResponse.setId(c.getId());
        contaResponse.setDescricao(c.getDescricao());
        contaResponse.setValor(c.getValor());
        contaResponse.setDataPagamento(c.getDataPagamento());
        contaResponse.setDataVencimento(c.getDataVencimento());
        contaResponse.setDataAtualizacao(c.getDataAtualizacao());
        contaResponse.setTipo(c.getTipo());
        contaResponse.setStatus(c.getStatus());
        contaResponse.setClienteFornecedor(c.getClienteFornecedor());
        contaResponse.setDataCriacao(c.getDataCriacao());

        return contaResponse;
    }


    public void deleteConta(Integer id){
        contasRepository.deleteById(id);
    }


    public boolean updateQuitar(Integer id) throws Exception {
        Contas conta = contasRepository.findById(id)
                .orElseThrow(() -> new Exception("Conta com ID " + id + " não encontrada!"));
        conta.setStatus(ContaStatus.PAGO);
        contasRepository.save(conta);
        return true;
    }


    public ResponseContaDTO getVencidas(){
        return null;
    }


    public ResponseContaDTO getPendentes(){
        return null;
    }


    public ResponseContaDTO getContasPorTipo(String tipo) {
        return null;
    }
}

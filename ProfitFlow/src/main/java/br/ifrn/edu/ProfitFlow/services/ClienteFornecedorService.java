package br.ifrn.edu.ProfitFlow.services;


import br.ifrn.edu.ProfitFlow.models.Cliente;
import br.ifrn.edu.ProfitFlow.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteFornecedorService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }


}

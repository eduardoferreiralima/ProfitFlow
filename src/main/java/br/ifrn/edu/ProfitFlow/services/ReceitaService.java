package br.ifrn.edu.ProfitFlow.services;

import br.ifrn.edu.ProfitFlow.dto.response.ResponseReceitaDTO;
import br.ifrn.edu.ProfitFlow.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class ReceitaService {
    @Autowired
    private ReceitaRepository receitaRepository;


    public ResponseReceitaDTO getPendentes(){
        return null;
    }

    public ResponseReceitaDTO getPendentes(Integer id){
        return null;
    }

    public ResponseReceitaDTO createReceita(){
        return null;
    }

    public ResponseReceitaDTO updateReceita(Integer id){
        return null;
    }

    public void deleteReceita(Integer id){
        receitaRepository.deleteById(id);
    }

    public List<ResponseReceitaDTO> getPeriodo(LocalDate inicio, LocalDate fim){
        return null;
    }

    public List<ResponseReceitaDTO> getStatus(String status){
        return null;
    }

    public List<ResponseReceitaDTO> getCategoria(String categoria){
        return null;
    }

}

package com.springboot.api.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.api.model.Funcionario;
import com.springboot.api.model.Lancamento;
import com.springboot.api.repository.FuncionarioRepository;
import com.springboot.api.repository.LancamentoRepositorio;
import com.springboot.api.service.exception.FuncionarioInexistenteOuInativaException;

@Service
public class LancamentoService {
    
	@Autowired
	private FuncionarioRepository service;
	
	@Autowired LancamentoRepositorio repositorio;
	
	
	public Lancamento salvar(@Valid Lancamento lancamento) {
		Funcionario funcionario = service.findById(lancamento.getFuncionario().getCodigo()).orElse(null);
		if(funcionario == null|| funcionario.isInativo()) {
			throw new FuncionarioInexistenteOuInativaException();
		}
		
		return repositorio.save(lancamento);
	}

}

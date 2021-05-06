package com.springboot.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.springboot.api.model.Funcionario;
import com.springboot.api.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

	@Autowired
	public FuncionarioRepository funcionarioRepository;

	public Funcionario atualizar(Funcionario funcionario, Long codigo) {

		Funcionario pessoaSalvo = buscarFuncionarioPeloCodigo(codigo);

		BeanUtils.copyProperties(funcionario, pessoaSalvo, "codigo");
		return funcionarioRepository.save(pessoaSalvo);
	}

	public void atualizarPropiedadesAtivos(Long codigo, Boolean ativo) {
		Funcionario pessoaSalvo = buscarFuncionarioPeloCodigo(codigo);
		pessoaSalvo.setAtivo(ativo);
		funcionarioRepository.save(pessoaSalvo);
		
	}

	public Funcionario buscarFuncionarioPeloCodigo(Long codigo) {
		Funcionario pessoaSalvo = funcionarioRepository.findById(codigo).get();
		if (pessoaSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalvo;

	}

}

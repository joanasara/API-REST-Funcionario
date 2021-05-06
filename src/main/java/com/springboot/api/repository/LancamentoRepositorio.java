package com.springboot.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.api.model.Lancamento;
import com.springboot.api.repository.lancamento.LancamentoRepositoryQuery;

@Repository
public interface LancamentoRepositorio extends JpaRepository<Lancamento, Long>,LancamentoRepositoryQuery {

	
	
	
	
}

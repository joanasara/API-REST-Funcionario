package com.springboot.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoFilter {
	
	
	private String descricao;
	
	@DateTimeFormat(pattern = "yy-MM-dd")
	private LocalDate dataVencimentoDe;
	
	@DateTimeFormat(pattern = "yy-MM-dd")
	private LocalDate dataVencimentoAte;
	
	
	
	
	
	
	

}

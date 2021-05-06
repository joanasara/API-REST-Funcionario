package com.springboot.api.resource;


import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.api.event.RecursoCriadoEvent;
import com.springboot.api.excepetion.ExceptionHandlerr.Erro;
import com.springboot.api.model.Lancamento;
import com.springboot.api.repository.LancamentoRepositorio;
import com.springboot.api.repository.filter.LancamentoFilter;
import com.springboot.api.service.LancamentoService;
import com.springboot.api.service.exception.FuncionarioInexistenteOuInativaException;

@RestController
@RequestMapping("/lancamento")
public class LancamentoController {

	@Autowired
	private LancamentoRepositorio lancamentoRepositorio;

	@Autowired
	private LancamentoService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private MessageSource message;
	
	

	@GetMapping
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter,Pageable pageable) {

		return lancamentoRepositorio.filtrar(lancamentoFilter, pageable);
	}

	@PostMapping
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamnetoSalvo = service.salvar(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamnetoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamnetoSalvo);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
		Lancamento lancamento = lancamentoRepositorio.findById(codigo).orElse(null);
		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}

	@ExceptionHandler({ FuncionarioInexistenteOuInativaException.class })
	public ResponseEntity<Object> FuncionarioInexistenteOuInativaException(
			FuncionarioInexistenteOuInativaException ex) {
		String mensagemUsuario = message.getMessage("funcionario.inexistente-ou-inativa", null,
				LocaleContextHolder.getLocale());
		String mensagemDensevlvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDensevlvedor));

		return ResponseEntity.badRequest().body(erros);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		lancamentoRepositorio.deleteById(codigo);
		
	}
	
	
	
	

}

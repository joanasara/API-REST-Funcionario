package com.springboot.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.springboot.api.event.RecursoCriadoEvent;
import com.springboot.api.model.Funcionario;
import com.springboot.api.repository.FuncionarioRepository;
import com.springboot.api.service.FuncionarioService;

@RestController
@RequestMapping("/{pessoa}")
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository pessoaRepositorio;
	
	@Autowired
	private  ApplicationEventPublisher publisher;
	
	@Autowired
	private FuncionarioService funcionarioService; 
	
	@GetMapping
	public List<Funcionario>listar(){
		
		return pessoaRepositorio.findAll();
	}
	
	
	@PostMapping
	public ResponseEntity<Funcionario> criar(@Valid @RequestBody  Funcionario pessoa, HttpServletResponse response) {
		Funcionario pessoaSalvo =pessoaRepositorio.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalvo.getCodigo()));
	    return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalvo);
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Funcionario> buscarPeloCodigo(@PathVariable Long codigo) {
		Funcionario pessoa =  pessoaRepositorio.findById(codigo).orElse(null);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		pessoaRepositorio.deleteById(codigo);
		
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Funcionario> atualizar(@PathVariable Long codigo, @Valid @RequestBody Funcionario funcionario){
		Funcionario pessoaSalvo = funcionarioService.atualizar(funcionario, codigo);
		return ResponseEntity.ok(pessoaSalvo);
		
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropiedadesAtivos(@PathVariable Long codigo,@RequestBody Boolean ativo) {
		funcionarioService.atualizarPropiedadesAtivos(codigo, ativo);
		
	}
	
	
	
	
	
	
	
	
}









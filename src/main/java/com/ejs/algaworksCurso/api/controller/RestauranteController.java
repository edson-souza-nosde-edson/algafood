package com.ejs.algaworksCurso.api.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ejs.algaworksCurso.domain.model.Restaurante;
import com.ejs.algaworksCurso.domain.services.RestauranteService;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@PutMapping("{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
			@RequestBody Restaurante restaurante){
		this.restauranteService.atualizar(restaurante, restauranteId);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
		return ResponseEntity.ok(uri);
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long id,
			@RequestBody Map<String, Object>  dadosAtualizar){
		this.restauranteService.atualizarParcial(dadosAtualizar, id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
		return ResponseEntity.ok(uri);
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<?> buscar(@PathVariable Long restauranteId){
		return ResponseEntity.ok(this.restauranteService.buscar(restauranteId));
	}
	
	@GetMapping("com-frete-gratis")
	public ResponseEntity<?> encontrarComFreteGratis(@RequestParam(name = "nome", required = true) String nome){
		/* Exemplo com classes*/
		//return ResponseEntity.ok(this.restauranteService.encontrarComFreteGratis(nome));
		/*Exemplo com a fábrica de specs*/
		
		return ResponseEntity.ok(this.restauranteService.encontrarComFreteGratis(nome));
	}
	
	@GetMapping("encocntrar-primeiro")
	public ResponseEntity<?> encontrarPrimeiro(){
		return ResponseEntity.ok(this.restauranteService.encontrarPrimeiro());
	}
	
	@GetMapping("encontrar-como")
	public ResponseEntity<?> find(@RequestParam(name = "nome", required = false) String nome,
			@RequestParam(required = false) BigDecimal taxaFreteInicial,
			@RequestParam(required = false) BigDecimal taxaFreteFinal){
		return ResponseEntity.ok(this.restauranteService.find(nome, taxaFreteInicial, taxaFreteFinal));
	}
	
	
	@GetMapping
	public ResponseEntity<?> listar(){
		return ResponseEntity.ok(this.restauranteService.listar());
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante){
		restaurante = this.restauranteService.salvar(restaurante);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(restaurante.getId())
				.toUri();
		return ResponseEntity.status(HttpStatus.CREATED).body(uri);
	}
	

}
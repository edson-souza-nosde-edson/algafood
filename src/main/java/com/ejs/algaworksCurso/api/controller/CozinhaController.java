package com.ejs.algaworksCurso.api.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ejs.algaworksCurso.api.model.StringUriResposta;
import com.ejs.algaworksCurso.api.model.in.cozinha.CozinhaIn;
import com.ejs.algaworksCurso.api.model.out.cozinha.CozinhaOut;
import com.ejs.algaworksCurso.api.openApi.controller.CozinhaControllerOpenApi;
import com.ejs.algaworksCurso.domain.services.CozinhaService;

@RestController
@RequestMapping("cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

	@Autowired
	private CozinhaService cozinhaService;

	@Override
	@PutMapping("{cozinhaId}")
	public ResponseEntity<StringUriResposta> atualizar(@RequestBody
			@Valid CozinhaIn cozinhaIn, @PathVariable("cozinhaId") Long cozinhaId) {
		this.cozinhaService.atualizar(cozinhaIn, cozinhaId);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
        String url = "http://" + uri.getAuthority() + uri.getPath();
        StringUriResposta uriRetorno = new StringUriResposta(url);
		return ResponseEntity.ok(uriRetorno);
	}

	@Override
	@GetMapping("{id}")
	public ResponseEntity<CozinhaOut> buscar(@PathVariable("id") Long id) {
		CozinhaOut cozinha = this.cozinhaService.buscar(id);
		return ResponseEntity.ok(cozinha);					
	}
	
	@Override
	@GetMapping("buscar-primeira")
	public ResponseEntity<CozinhaOut> buscarPrimeira(){
		return ResponseEntity.ok(this.cozinhaService.buscarPrimeira());
	}

	@Override
	@GetMapping
	public Page<CozinhaOut> listar(@PageableDefault(size = 10) Pageable pageable) {
		return this.cozinhaService.listar(pageable);
	}
	
	@Override
	@DeleteMapping("{cozinhaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId){
			this.cozinhaService.remover(cozinhaId);
	}

	@Override
	@PostMapping
	public ResponseEntity<StringUriResposta> salvar(@RequestBody @Valid CozinhaIn cozinhaIn) {
		CozinhaOut cozinha = this.cozinhaService.salvar(cozinhaIn);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(cozinha.getId())
				.toUri();
		StringUriResposta uriResposta = new StringUriResposta("http://" + uri.getAuthority() + uri.getPath());
		return ResponseEntity.status(HttpStatus.CREATED).body(uriResposta);
	}
	
}

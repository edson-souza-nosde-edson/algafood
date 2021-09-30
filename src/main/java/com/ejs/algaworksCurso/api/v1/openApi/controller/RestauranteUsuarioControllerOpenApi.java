package com.ejs.algaworksCurso.api.v1.openApi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.ejs.algaworksCurso.api.v1.model.out.usuario.UsuarioOut;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Restaurante Proprietários")
public interface RestauranteUsuarioControllerOpenApi {

	@ApiOperation(value="Associa responsável a restaurante")
	ResponseEntity<Void> associarResponsavel(Long restauranteId, Long usuarioId);

	@ApiOperation(value="Desassocia responsável a restaurante")
	ResponseEntity<Void> desassociarResponsavel(Long restauranteId, Long usuarioId);

	@ApiOperation(value="Lista responsáveis")
	ResponseEntity<CollectionModel<UsuarioOut>> listarResponsaveis(Long restauranteId);

}
package com.ejs.algaworksCurso.api.model.dto.in;

import javax.validation.constraints.NotEmpty;

public class FormaPagamentoIn {
	
	@NotEmpty
	private String descricao;

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
}

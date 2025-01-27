package com.ejs.algaworksCurso.api.v1.model.out.restautante;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.ejs.algaworksCurso.api.v1.model.in.cozinha.CozinhaResumidaIdDTO;
import com.ejs.algaworksCurso.api.v1.model.out.endereco.EnderecoOut;

@Relation(collectionRelation = "restaurantes")
public class RestauranteOut extends RepresentationModel<RestauranteOut> {

	private Long id;
	private String nome;
	private boolean aberto;
	private boolean ativo;
	private CozinhaResumidaIdDTO cozinha;
	private BigDecimal taxaFrete;
	private EnderecoOut endereco;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the aberto
	 */
	public boolean isAberto() {
		return aberto;
	}
	/**
	 * @param aberto the aberto to set
	 */
	public void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	/**
	 * @return the ativo
	 */
	public boolean isAtivo() {
		return ativo;
	}
	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	/**
	 * @return the cozinha
	 */
	public CozinhaResumidaIdDTO getCozinha() {
		return cozinha;
	}
	/**
	 * @param cozinha the cozinha to set
	 */
	public void setCozinha(CozinhaResumidaIdDTO cozinha) {
		this.cozinha = cozinha;
	}
	/**
	 * @return the taxaFrete
	 */
	public BigDecimal getTaxaFrete() {
		return taxaFrete;
	}
	/**
	 * @param taxaFrete the taxaFrete to set
	 */
	public void setTaxaFrete(BigDecimal taxaFrete) {
		this.taxaFrete = taxaFrete;
	}
	/**
	 * @return the endereco
	 */
	public EnderecoOut getEndereco() {
		return endereco;
	}
	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(EnderecoOut endereco) {
		this.endereco = endereco;
	}
	
	
}

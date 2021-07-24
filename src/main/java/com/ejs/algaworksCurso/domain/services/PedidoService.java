package com.ejs.algaworksCurso.domain.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ejs.algaworksCurso.api.model.in.itensPedido.ItensPedidoIn;
import com.ejs.algaworksCurso.api.model.in.pedido.PedidoIn;
import com.ejs.algaworksCurso.api.model.out.pedido.PedidoOut;
import com.ejs.algaworksCurso.api.model.out.pedido.PedidoResumidoDTO;
import com.ejs.algaworksCurso.domain.exception.NegocioException;
import com.ejs.algaworksCurso.domain.exception.PedidoNaoEncontradaException;
import com.ejs.algaworksCurso.domain.exception.RestauranteNaoEncontradoException;
import com.ejs.algaworksCurso.domain.exception.UsuarioNaoEncontradoException;
import com.ejs.algaworksCurso.domain.model.FormaPagamento;
import com.ejs.algaworksCurso.domain.model.ItemPedido;
import com.ejs.algaworksCurso.domain.model.Pedido;
import com.ejs.algaworksCurso.domain.model.Produto;
import com.ejs.algaworksCurso.domain.model.Restaurante;
import com.ejs.algaworksCurso.domain.model.Usuario;
import com.ejs.algaworksCurso.domain.model.filter.PedidoFilter;
import com.ejs.algaworksCurso.domain.repository.PedidoRepository;
import com.ejs.algaworksCurso.helper.pedido.PedidoAssembler;
import com.ejs.algaworksCurso.helper.pedido.PedidoDisAssembler;
import com.ejs.algaworksCurso.infrastructure.repository.spec.PedidoSpecs;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PedidoDisAssembler pedidoDisAssembler;
	
	@Autowired 
	PedidoAssembler pedidoAssembler;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired 
	FormaPagamentoService formaPagamentoService;
	
	
	@Transactional
	public void cancelarPedido( String codigoPedido) {
		Pedido pedido = this.buscarOuFalhar(codigoPedido);
		pedido.cancelar();
	}
	
	@Transactional
	public void confirmarPedido(String codigoPedido) {
		Pedido pedido = this.buscarOuFalhar(codigoPedido);
		pedido.confirmar();
		
	}
	
	public PedidoOut buscar(String codigoPedido) {
		Pedido pedido = this.buscarOuFalhar(codigoPedido);
		return this.pedidoDisAssembler.pedidoToPedidoOut(pedido);
	}
	
	@Transactional
	public void entregarPedido(String codigoPedido) {
		Pedido pedido = this.buscarOuFalhar(codigoPedido);
		pedido.entregar();
	}
	
	public Page<PedidoResumidoDTO> listar(Pageable pageable, PedidoFilter filtro){
		pageable = this.deParaPageable(pageable);
		Page<Pedido> pedidos = this.pedidoRepository.findAll(PedidoSpecs.usandoFiltor(filtro), pageable);
		List<PedidoResumidoDTO> pedidosOut = pedidos.stream()
				.map(pedido -> this.pedidoDisAssembler.pedidoToPedidoResumidoDTO(pedido))
				.collect(Collectors.toList());
		
		Page<PedidoResumidoDTO> pedidosPage = new PageImpl<PedidoResumidoDTO>(pedidosOut, pageable, pedidos.getTotalElements());
		
		return pedidosPage;
	}
	
	@Transactional
	public PedidoOut salvar(PedidoIn pedidoIn) {
		try {
			
			Restaurante restaurante = this.restauranteService.buscarOuFalhar(pedidoIn.getRestaurante().getId());
			
			FormaPagamento fp = this.formaPagamentoService.buscarOuFalhar(pedidoIn.getFormaPagamento().getId());
			
			if(!restaurante.getFormasPagamento().contains(fp)) {
				throw new NegocioException(
						String.format("Restaurante de código %d não aceita forma de pagamento de código %d", 
								restaurante.getId(), pedidoIn.getFormaPagamento().getId()));
			}
			
			Pedido pedido = this.pedidoAssembler.pedidoInToPedido(pedidoIn);
			this.subTotalPedido(pedidoIn, pedido);
			Usuario cliente = this.usuarioService.buscarOuFalhar(1L);
			pedido.setValorTotal(restaurante.getTaxaFrete().add(pedido.getSubTotal()));
			pedido.setCliente(cliente);
			pedido.setTaxaFrete(restaurante.getTaxaFrete());
			pedido.criar();
			pedido = this.pedidoRepository.save(pedido);
			return this.pedidoDisAssembler.pedidoToPedidoOut(pedido);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(String.format("Restaurante de código %d não existe", pedidoIn.getRestaurante().getId()));
		} catch (UsuarioNaoEncontradoException e) {
			throw new NegocioException("Falha ao buscar usuario.");
		}
		
	}
	
	
	public Pedido buscarOuFalhar(String codigoPedido) {
		return this.pedidoRepository.findByCodigo(codigoPedido)
				.orElseThrow( () -> new PedidoNaoEncontradaException(codigoPedido));
	}
	
	/*
	 * Métodos privados
	 */
	
	private void subTotalPedido(PedidoIn pedidoIn, Pedido pedido) {
		BigDecimal subTotal = new BigDecimal(0);
		for ( ItensPedidoIn in : pedidoIn.getItens()) {
			Produto p = this.produtoService.buscarOuFalhar(in.getProdutoId());
			BigDecimal precoProduto = p.getPreco();
			BigDecimal quantidadeProduto = new BigDecimal(in.getQuantidade()).setScale(2, RoundingMode.FLOOR);
			BigDecimal multiplication = precoProduto.multiply(quantidadeProduto).setScale(2, RoundingMode.FLOOR);
			subTotal = subTotal.add(multiplication).setScale(2, RoundingMode.FLOOR);
			
			for( ItemPedido itemP : pedido.getItens()){
				if ( itemP.getProduto().getId().equals(p.getId())) {
					itemP.setPrecoTotal(multiplication);
					itemP.setPrecoUnitario(p.getPreco());
					itemP.setPedido(pedido);
				}
			}
		}
		pedido.setSubTotal(subTotal);
	}
	
	
	
	/**
	 * Verifica se os campos passados na requisição podem ser usados como filtro da pesquisa
	 * @param pageable
	 * @return
	 */
	private Pageable deParaPageable( Pageable pageable) {
		Map<String, String> map = Map.of("codigo", "codigo", "subTotal", "subTotal", "taxaFrete", "taxaFrete",
				"valorTotal", "valoTotal", "restaurante.id", "restauranteId", "nomeCliente", "cliente.nome");
		
		
		var orders = pageable.getSort().stream()
				.filter(order -> map.containsKey(order.getProperty()) )
				.map(order -> new Sort.Order(order.getDirection(), map.get(order.getProperty())))
				.collect(Collectors.toList());
		
		return PageRequest.of( pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
	}
		
	
	
	
	
	
	
	
	
	
	
}

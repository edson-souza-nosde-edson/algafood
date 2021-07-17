package com.ejs.algaworksCurso.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.ejs.algaworksCurso.domain.model.Pedido;

public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {
	
	@Query("FROM Pedido p JOIN FETCH p.cliente JOIN FETCH p.restaurante r JOIN FETCH r.cozinha")
	List<Pedido> findAll();
	
	Optional<Pedido> findByCodigo(String codigo);
}

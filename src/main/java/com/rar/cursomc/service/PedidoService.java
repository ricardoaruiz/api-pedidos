package com.rar.cursomc.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rar.cursomc.domain.PagamentoComBoleto;
import com.rar.cursomc.domain.Pedido;
import com.rar.cursomc.domain.enums.EstadoPagamento;
import com.rar.cursomc.repository.ItemPedidoRespository;
import com.rar.cursomc.repository.PagamentoRespository;
import com.rar.cursomc.repository.PedidoRespository;
import com.rar.cursomc.service.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRespository pedidoRespository;
	
	@Autowired
	private PagamentoRespository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRespository itemPedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	public Pedido load(Integer id) {
		return this.pedidoRespository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(
						"Recurso não existente com Id " + id + ", Nome: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido pedido) {

		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamentoComBoleto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagamentoComBoleto, pedido.getInstante());
		}
		
		final Pedido insertedPedido = pedidoRespository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		pedido.getItens().stream().forEach(item -> {
			item.setDesconto(0.00);
			item.setPreco(produtoService.load(item.getProduto().getId()).getPreco());
			item.setPedido(insertedPedido);
		});
		this.itemPedidoRepository.saveAll(pedido.getItens());
		
		return pedido;
	}
	
}

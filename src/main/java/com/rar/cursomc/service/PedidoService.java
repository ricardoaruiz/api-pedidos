package com.rar.cursomc.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rar.cursomc.domain.Cliente;
import com.rar.cursomc.domain.PagamentoComBoleto;
import com.rar.cursomc.domain.Pedido;
import com.rar.cursomc.domain.Produto;
import com.rar.cursomc.domain.enums.EstadoPagamento;
import com.rar.cursomc.dto.PaginacaoDTO;
import com.rar.cursomc.repository.ItemPedidoRespository;
import com.rar.cursomc.repository.PagamentoRespository;
import com.rar.cursomc.repository.PedidoRespository;
import com.rar.cursomc.security.domain.User;
import com.rar.cursomc.security.domain.enums.Profile;
import com.rar.cursomc.security.exception.AuthorizationException;
import com.rar.cursomc.security.service.UserService;
import com.rar.cursomc.service.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	private static final Logger LOG = LoggerFactory.getLogger(PedidoService.class);
	
	@Autowired
	private PedidoRespository pedidoRespository;
	
	@Autowired
	private PagamentoRespository pagamentoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRespository itemPedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private EmailService emailService;
	
	public Page<Pedido> listAllPaginated(PaginacaoDTO paginacao) {
		User authenticated = UserService.authenticated();
		
		if (authenticated == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		if (!authenticated.hasHole(Profile.ADMIN)) {
			Cliente client = this.clienteService.load(authenticated.getId());		
			return this.pedidoRespository.findByCliente(client, paginacao.buildRequestPage());
		} else {
			return this.pedidoRespository.findAll(paginacao.buildRequestPage());			
		}
	}
	
	public Pedido load(Integer id) {
		Pedido order = this.pedidoRespository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(
						"Recurso não existente com Id " + id + ", Nome: " + Pedido.class.getName()));
		
		User authenticated = UserService.authenticated();
		Cliente orderClient = order.getCliente();
		
		if (!authenticated.getId().equals(orderClient.getId()) && !authenticated.hasHole(Profile.ADMIN)) {
			throw new AuthorizationException("Acesso negado");
		}
		
		return order;
	}

	@Transactional
	public Pedido insert(Pedido pedido) {

		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(clienteService.load(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamentoComBoleto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagamentoComBoleto, pedido.getInstante());
		}
		
		final Pedido insertedPedido = pedidoRespository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		pedido.getItens().stream().forEach(item -> {
			Produto produto = produtoService.load(item.getProduto().getId());
			item.setProduto(produto);
			item.setDesconto(0.00);
			item.setPreco(produto.getPreco());
			item.setPedido(insertedPedido);
		});
		this.itemPedidoRepository.saveAll(pedido.getItens());
		
		try {
			emailService.sendOrderConfirmationHtmlEmail(pedido);
		} catch(Exception e) {
			LOG.error("O email de confirmação do pedido " + pedido.getId() + " não foi enviado.");
		}
		
		return pedido;
	}
	
}

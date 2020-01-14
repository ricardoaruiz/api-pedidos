package com.rar.cursomc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rar.cursomc.domain.Categoria;
import com.rar.cursomc.domain.Cidade;
import com.rar.cursomc.domain.Cliente;
import com.rar.cursomc.domain.Endereco;
import com.rar.cursomc.domain.Estado;
import com.rar.cursomc.domain.ItemPedido;
import com.rar.cursomc.domain.Pagamento;
import com.rar.cursomc.domain.PagamentoComBoleto;
import com.rar.cursomc.domain.PagamentoComCartao;
import com.rar.cursomc.domain.Pedido;
import com.rar.cursomc.domain.Produto;
import com.rar.cursomc.domain.enums.EstadoPagamento;
import com.rar.cursomc.domain.enums.TipoCliente;
import com.rar.cursomc.repository.CategoriaRepository;
import com.rar.cursomc.repository.CidadeRepository;
import com.rar.cursomc.repository.ClienteRespository;
import com.rar.cursomc.repository.EnderecoRespository;
import com.rar.cursomc.repository.EstadoRepository;
import com.rar.cursomc.repository.ItemPedidoRespository;
import com.rar.cursomc.repository.PedidoRespository;
import com.rar.cursomc.repository.ProdutoRespository;

@Service
public class DBService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRespository produtoRespository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRespository clienteRespository;
	
	@Autowired
	private EnderecoRespository enderecoRespository;
	
	@Autowired
	private PedidoRespository pedidoRespository;

	@Autowired
	private ItemPedidoRespository itemPedidoRespository;
	
	public void instantiateTestDatabase() throws ParseException {
		Categoria cat1 = new Categoria("Informática");
		Categoria cat2 = new Categoria("Escritório");
		Categoria cat3 = new Categoria("Cama mesa e banho");
		Categoria cat4 = new Categoria("Eletrônicos");
		Categoria cat5 = new Categoria("Jardinagem");
		Categoria cat6 = new Categoria("Decoração");
		Categoria cat7 = new Categoria("Perfumaria");
		Categoria cat8 = new Categoria("Fitness");
		Categoria cat9 = new Categoria("Geek");
		Categoria cat10 = new Categoria("Diversos");
		
		Produto p1 = new Produto("Computador", 2000.00);
		Produto p2 = new Produto("Impressora", 300.00);
		Produto p3 = new Produto("Mouse", 80.00);
		
		// Categorias
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9, cat10));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		produtoRespository.saveAll(Arrays.asList(p1, p2, p3));
		
		// Estados		
		Estado sp = new Estado("São Paulo");
		Estado mg = new Estado("Minas Gerais");
		estadoRepository.saveAll(Arrays.asList(sp, mg));
				
		// Cidades
		Cidade arturNogueira = new Cidade("Artur Nogueira", sp);
		Cidade cosmopolis = new Cidade("Cosmópolis", sp);
		Cidade uberlandia = new Cidade("Uberlandia", mg);
		Cidade beloHorizonte = new Cidade("Belo Horizonte", mg);
		cidadeRepository.saveAll(Arrays.asList(arturNogueira, cosmopolis, uberlandia, beloHorizonte));
		
		//Cliente e Endereco
		Cliente c1 = new Cliente("Ricardo Ruiz", "ricardo.almendro.ruiz@gmail.com", "26852132870", TipoCliente.PESSOA_FISICA);
		c1.getTelefones().addAll(Arrays.asList("19999412206", "1992237912"));
		Endereco endereco1C1 = new Endereco("Alameda dos Resedas", "154", null, "Portal dos Manacás", "13162322", c1, arturNogueira);
		Endereco endereco2C1 = new Endereco("Alameda dos Resedas1", "1541", null, "Portal dos Manacás1", "13162323", c1, cosmopolis);
		c1.getEnderecos().addAll(Arrays.asList(endereco1C1, endereco2C1));
		
		this.clienteRespository.save(c1);
		this.enderecoRespository.saveAll(Arrays.asList(endereco1C1, endereco2C1));
		
		// Pedido
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(sdf.parse("30/09/2017 10:32"), c1, c1.getEnderecos().get(0));
		Pagamento pagto1 = new PagamentoComCartao(EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pedido ped2 = new Pedido(sdf.parse("10/10/2017 12:22"), c1, c1.getEnderecos().get(1));
		Pagamento pagto2 = new PagamentoComBoleto(EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 23:59"), null);
		ped2.setPagamento(pagto2);
		
		this.pedidoRespository.saveAll(Arrays.asList(ped1, ped2));
		
		// Itens dos pedidos
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		itemPedidoRespository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
	
}

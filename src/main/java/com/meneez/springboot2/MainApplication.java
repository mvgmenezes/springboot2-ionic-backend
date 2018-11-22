package com.meneez.springboot2;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.meneez.springboot2.domain.Categoria;
import com.meneez.springboot2.domain.Cidade;
import com.meneez.springboot2.domain.Cliente;
import com.meneez.springboot2.domain.Endereco;
import com.meneez.springboot2.domain.Estado;
import com.meneez.springboot2.domain.ItemPedido;
import com.meneez.springboot2.domain.Pagamento;
import com.meneez.springboot2.domain.PagamentoComBoleto;
import com.meneez.springboot2.domain.PagamentoComCartao;
import com.meneez.springboot2.domain.Pedido;
import com.meneez.springboot2.domain.Produto;
import com.meneez.springboot2.domain.enums.EstadoPagamento;
import com.meneez.springboot2.domain.enums.TipoCliente;
import com.meneez.springboot2.repositories.CategoriaRepository;
import com.meneez.springboot2.repositories.CidadeRepository;
import com.meneez.springboot2.repositories.ClienteRepository;
import com.meneez.springboot2.repositories.EnderecoRepository;
import com.meneez.springboot2.repositories.EstadoRepository;
import com.meneez.springboot2.repositories.ItemPedidoRepository;
import com.meneez.springboot2.repositories.PagamentoRepository;
import com.meneez.springboot2.repositories.PedidoRepository;
import com.meneez.springboot2.repositories.ProdutoRepository;

@SpringBootApplication
public class MainApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	
	//usado para teste sempre que iniciar o app rodar
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		//Categoria pode ter varios produtos 
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		//Produtos pode ter varias categorias tambem
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		//salvando no banco de dados
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		
		//salvando os produtos
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		
		//criando os estados
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		//primiero deve salvar os estados pois ele é referencia em outra tabela
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		//Criando o cliente e seus enderecos
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com","123123123", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("22222333", "988899889"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 300", "Jardim", "22332223", cli1, c1);
		
		Endereco e2 = new Endereco(null, "Avenida matos", "105", "Sala 900", "Centro", "3877012", cli1, c2);
				
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		//Criando os pedidos
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:00"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 11:02"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		//criando os itens de pedido
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
	}
}

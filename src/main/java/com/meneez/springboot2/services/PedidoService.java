package com.meneez.springboot2.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meneez.springboot2.domain.ItemPedido;
import com.meneez.springboot2.domain.PagamentoComBoleto;
import com.meneez.springboot2.domain.Pedido;
import com.meneez.springboot2.domain.enums.EstadoPagamento;
import com.meneez.springboot2.repositories.ItemPedidoRepository;
import com.meneez.springboot2.repositories.PagamentoRepository;
import com.meneez.springboot2.repositories.PedidoRepository;
import com.meneez.springboot2.services.email.EmailService;
import com.meneez.springboot2.services.exceptions.ObjectNotFoundException;

/**
 * Classe de acesso aos servicos
 * @author mvgmenezes
 *
 */

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private EmailService emailService;

	public Pedido find(Integer id) {
		
		//Acessar os dados atraves do Repositories
		//No Java 8 foi alterado para o objeto Optional e o nome de findOne para findById
		
		
		//Optional<Pedido> obj = repo.findById(id);
		//return obj.orElse(null);
		
		
		// usando o lambda para substituir o bloco abaixo:
		//if (obj == null) {
		//	throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
		//	+ ", Tipo: " + Pedido.class.getName());
		//}
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName(), null));

	}
	
	//Inserindo um pedido
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		
		//buscando todos os dados do cliente pelo id passado
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		
		//insere como pendemte
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		//fazendo a associacao de mao dupla o pagamento tem que conhecer o seu pedido
		obj.getPagamento().setPedido(obj);
		
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			//preenchendo a data de vencimento
			boletoService.preecherPagamentoComBoleto(pagto, obj.getInstante());
			
		}
		
		//salvando o pedido
		obj = repo.save(obj);
		
		//salvando o pagamento
		pagamentoRepository.save(obj.getPagamento());
		
		//salvando os itens de pedido
		for(ItemPedido item :obj.getItens()) {
			item.setDesconto(0.0);
			//busca o produto atraves do id
			item.setProduto(produtoService.find(item.getProduto().getId()));
		
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		
		//enviando email sobre o pedido
		emailService.sendOrderConfirmationEmail(obj);
		System.out.println(obj);
		return obj;
	}
}

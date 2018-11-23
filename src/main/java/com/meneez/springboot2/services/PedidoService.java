package com.meneez.springboot2.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meneez.springboot2.domain.ItemPedido;
import com.meneez.springboot2.domain.PagamentoComBoleto;
import com.meneez.springboot2.domain.Pedido;
import com.meneez.springboot2.domain.enums.EstadoPagamento;
import com.meneez.springboot2.repositories.ItemPedidoRepository;
import com.meneez.springboot2.repositories.PagamentoRepository;
import com.meneez.springboot2.repositories.PedidoRepository;
import com.meneez.springboot2.repositories.ProdutoRepository;
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
	private ItemPedidoRepository itemPedidoRepository;

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
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
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
			//busca o preco atraves do id
			item.setPreco(produtoService.find(item.getProduto().getId()).getPreco());
			item.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
}

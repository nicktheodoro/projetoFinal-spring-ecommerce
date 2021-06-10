package org.serratec.com.backend.ecommerce.controllers;

import java.util.List;

import org.serratec.com.backend.ecommerce.entities.dto.ProdutosPedidosDto;
import org.serratec.com.backend.ecommerce.entities.dto.PedidoDto;
import org.serratec.com.backend.ecommerce.exceptions.CarrinhoException;
import org.serratec.com.backend.ecommerce.exceptions.DataIntegrityViolationException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.exceptions.ProdutoException;
import org.serratec.com.backend.ecommerce.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	PedidoService service;

	@GetMapping
	public ResponseEntity<List<PedidoDto>> getAll() {
		return new ResponseEntity<List<PedidoDto>>(service.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{numeroPedido}")
	public ResponseEntity<PedidoDto> getById(@PathVariable String numeroPedido) throws EntityNotFoundException {
		return new ResponseEntity<PedidoDto>(service.getByNumeroPedido(numeroPedido), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PedidoDto> create(@RequestBody PedidoDto purchase) throws EntityNotFoundException, ProdutoException {
		return new ResponseEntity<PedidoDto>(service.order(purchase), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PedidoDto> update(@PathVariable Long id, @RequestBody PedidoDto purchase)
			throws EntityNotFoundException {
		return new ResponseEntity<PedidoDto>(service.update(id, purchase), HttpStatus.ACCEPTED);
	}

	@PutMapping("/atualizar/{numeroPedido}")
	public ResponseEntity<PedidoDto> adionarProduto(@PathVariable String numeroPedido,
			@RequestBody List<ProdutosPedidosDto> productOrderDto) throws EntityNotFoundException {
		return new ResponseEntity<PedidoDto>(service.updateOrder(numeroPedido, productOrderDto), HttpStatus.ACCEPTED);
	}

	@PutMapping("/remover-produto-pedido/{numeroPedido}")
	public ResponseEntity<PedidoDto> delete(@PathVariable String numeroPedido,
			@RequestBody List<ProdutosPedidosDto> productOrderDto) throws EntityNotFoundException, CarrinhoException {
		service.deletarProdutoOrder(numeroPedido, productOrderDto);
		return new ResponseEntity<PedidoDto>(service.deletarProdutoOrder(numeroPedido, productOrderDto), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id)
			throws EntityNotFoundException, DataIntegrityViolationException {
		service.delete(id);
		return new ResponseEntity<String>("Categoria com id: " + id + " deletada com sucesso!", HttpStatus.NO_CONTENT);
	}

}

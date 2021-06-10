package org.serratec.com.backend.ecommerce.controllers;

import java.io.IOException;
import java.util.List;

import org.serratec.com.backend.ecommerce.entities.ImagemEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ProdutoDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.exceptions.ProdutoException;
import org.serratec.com.backend.ecommerce.services.ImagemService;
import org.serratec.com.backend.ecommerce.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	ProdutoService service;
	
	@Autowired
	ImagemService imagemService;

	@GetMapping
	public ResponseEntity<List<ProdutoDto>> getAll() {
		return new ResponseEntity<List<ProdutoDto>>(service.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProdutoDto> getById(@PathVariable Long id) throws EntityNotFoundException {
		return new ResponseEntity<ProdutoDto>(service.getById(id), HttpStatus.OK);
	}

	@GetMapping("/nome")
	public ResponseEntity<ProdutoDto> getByName(@RequestParam String nome) throws EntityNotFoundException {
		return new ResponseEntity<ProdutoDto>(service.getByName(nome), HttpStatus.OK);
	}
	
	@GetMapping("/{id}/imagem")
	public ResponseEntity <byte[]> getImage(@PathVariable Long id){
		ImagemEntity imagem = imagemService.getImagem(id);
		HttpHeaders header = new HttpHeaders();
		header.add("content-length", String.valueOf(imagem.getData().length));
		header.add("content-type", imagem.getMimeType());
		return new ResponseEntity<byte[]>(imagemService.getImagem(id).getData(),header, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ProdutoDto> create(@RequestParam MultipartFile file, @RequestPart ProdutoDto product)
			throws EntityNotFoundException, ProdutoException, IOException {
		return new ResponseEntity<ProdutoDto>(service.create(product, file), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProdutoDto> update(@PathVariable Long id, @RequestBody ProdutoDto category)
			throws EntityNotFoundException {
		return new ResponseEntity<ProdutoDto>(service.update(id, category), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws EntityNotFoundException, ProdutoException {
		service.delete(id);

		return new ResponseEntity<String>("Categoria com id: " + id + " deletada com sucesso!", HttpStatus.OK);
	}
}

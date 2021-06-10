package org.serratec.com.backend.ecommerce.services;

import java.io.IOException;

import javax.transaction.Transactional;

import org.serratec.com.backend.ecommerce.entities.ImagemEntity;
import org.serratec.com.backend.ecommerce.entities.ProdutoEntity;
import org.serratec.com.backend.ecommerce.repositories.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagemService {
	@Autowired
	ImagemRepository repository;
	
	@Transactional
	public ImagemEntity create(ProdutoEntity entity, MultipartFile file) throws IOException {
		ImagemEntity imagem = new ImagemEntity();
		imagem.setProduto(entity);
		imagem.setData(file.getBytes());
		imagem.setMimeType(file.getContentType());
		imagem.setNome("Imagem");
		return repository.save(imagem);
	}
	
	@Transactional
	public ImagemEntity getImagem(Long id) {
		ImagemEntity imageClient = repository.findByProdutoId(id);
		return imageClient;
	}
	
	@Transactional
	public void deletarImagemProduto(Long id) {
		ImagemEntity entity = repository.findByProdutoId(id);
		repository.delete(entity);
	}
}

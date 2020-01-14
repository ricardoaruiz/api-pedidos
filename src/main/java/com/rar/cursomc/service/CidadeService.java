package com.rar.cursomc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rar.cursomc.domain.Cidade;
import com.rar.cursomc.repository.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;	
	
	public List<Cidade> listAll() {
		return this.cidadeRepository.findAll();
	}
	
}

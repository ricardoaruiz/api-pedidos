package com.rar.cursomc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rar.cursomc.domain.Estado;
import com.rar.cursomc.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> listAll() {
		return this.estadoRepository.findAll();
	}
	
}

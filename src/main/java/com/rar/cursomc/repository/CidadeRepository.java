package com.rar.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rar.cursomc.domain.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}

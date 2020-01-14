package com.rar.cursomc.dto;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

public class PaginacaoDTO implements Serializable {

	private static final long serialVersionUID = -1605640161324358007L;
	
	private Integer page; 
	private Integer linesPerPage;
	private String orderBy;
	private String direction;

	public Integer getPage() {
		return page == null ? 0 : page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLinesPerPage() {
		return linesPerPage == null ? 24 : linesPerPage;
	}

	public void setLinesPerPage(Integer linesPerPage) {
		this.linesPerPage = linesPerPage;
	}

	public String getOrderBy() {
		return orderBy == null ? "id" : orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getDirection() {
		return direction == null ? "ASC" : direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public PageRequest buildRequestPage() {
		return PageRequest.of(
				this.getPage(), 
				this.getLinesPerPage(), 
				Direction.fromString(this.getDirection()), 
				this.getOrderBy());
	}
	
}

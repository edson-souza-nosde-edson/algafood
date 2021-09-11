package com.ejs.algaworksCurso.api.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageableWrapper<T> extends PageImpl<T> {
	private static final long serialVersionUID = 1L;

	private Pageable pageable;
	
	public PageableWrapper(Page<T> page, Pageable pageable) {
		super(page.getContent(), pageable, page.getTotalElements());
		this.pageable = pageable;
	}
	
	@Override
	public Pageable getPageable() {
		return this.pageable;
	}
	
}

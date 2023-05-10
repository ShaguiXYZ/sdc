package com.shagui.sdc.api.domain;

import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.shagui.sdc.util.Ctes.JPA;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPageInfo {
	private Integer page;
	private Integer size;

	public RequestPageInfo(Integer page) {
		this(page, JPA.ELEMENTS_BY_PAGE);
	}

	public RequestPageInfo(Integer page, Integer size) {
		this.page = Objects.nonNull(page) ? page : 0;
		this.size = Objects.nonNull(size) && size > 0 ? size : JPA.ELEMENTS_BY_PAGE;
	}

	public Pageable getPageable() {
		return PageRequest.of(page, size);
	}


	public Pageable getPageable(Sort sort) {
		return PageRequest.of(page, size, sort);
	}
}

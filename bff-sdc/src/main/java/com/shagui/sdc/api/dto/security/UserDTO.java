package com.shagui.sdc.api.dto.security;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
public class UserDTO {
	private String userName;
	private String email;
	private PersonDTO person;
	private List<AuthorityDTO> authorities = new ArrayList<>();

	public UserDTO withAuthorities(List<AuthorityDTO> authorities) {
		this.authorities = authorities.stream().toList();

		return this;
	}
}

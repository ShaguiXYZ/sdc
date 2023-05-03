package com.shagui.sdc.api.dto.security;

import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
	private String userName;
	private List<AuthorityDTO> authorities;
	private String token;
	
	public AuthorityDTO getAuthority() {
		return authorities.get(0);
	}
}

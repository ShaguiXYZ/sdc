package com.shagui.sdc.api.view.security;

import java.util.ArrayList;
import java.util.List;

import com.shagui.sdc.api.dto.security.UserDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserView {
	private String userName;
	private String email;
	private String name;
	private String surname;
	private String secondSurname;
	private List<String> authorities;

	public UserView(UserDTO source) {
		this.userName = source.getUserName();
		this.email = source.getEmail();
		this.name = source.getPerson().getName();
		this.surname = source.getPerson().getSurname();
		this.secondSurname = source.getPerson().getSecondSurname();

		this.authorities = new ArrayList<>();
		source.getAuthorities().forEach(a -> authorities.add(a.getAuthority()));
	}
}

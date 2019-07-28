package com.throughlettersandcode.security;

import java.util.Collection;

import com.throughlettersandcode.model.UserEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SystemUser extends User {

	private static final long serialVersionUID = 1L;

	private UserEntity user;

	public SystemUser(UserEntity user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getEmail(), user.getPassword(), authorities);
		this.user = user;
	}

	public UserEntity getUser() {
		return user;
	}

}
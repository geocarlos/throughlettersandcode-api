package com.throughlettersandcode.security;

import java.util.Collection;

import com.throughlettersandcode.model.UserEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SystemUser extends User {

	private static final long serialVersionUID = 1L;

	private UserEntity userEntity;

	public SystemUser(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {
		super(userEntity.getEmail(), userEntity.getPassword(), authorities);
		this.userEntity = userEntity;
	}

	public UserEntity getUser() {
		return userEntity;
	}

}
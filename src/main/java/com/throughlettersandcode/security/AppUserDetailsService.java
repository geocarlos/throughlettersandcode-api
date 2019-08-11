package com.throughlettersandcode.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.throughlettersandcode.model.UserEntity;
import com.throughlettersandcode.repository.UserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserEntity> userOptional = userRepository.findByEmail(email);
		UserEntity userEntity = userOptional.orElseThrow(() -> new UsernameNotFoundException("Username or password incorrect"));
		return new SystemUser(userEntity, getPermissions(userEntity));
	}


	private Collection<? extends GrantedAuthority> getPermissions(UserEntity userEntity) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		userEntity.getPermissions().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescription().toUpperCase())));
		return authorities;
	}

}
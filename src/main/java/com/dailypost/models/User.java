package com.dailypost.models;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {
	public User() {}
	public User(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 20, nullable = false, unique = true)
	private String username;
	@Column(length = 60, nullable = false)
	private String password;
	@Column(length = 15, nullable = false)
	private String role;

	
	
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	@Override
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	@Override
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public String getRole() {return role;}
	public void setRole(String role) {this.role = role;}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		if(this.role.equalsIgnoreCase("ADMIN")) {authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));}
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}

	
	
}

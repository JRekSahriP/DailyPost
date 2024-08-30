package com.dailypost.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailypost.exceptions.UsernameAlreadyExistsException;
import com.dailypost.models.Post;
import com.dailypost.models.User;
import com.dailypost.models.DTO.UserDTO;
import com.dailypost.repositories.UserRepository;

@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private RememberMeServices rememberMeServices;
	
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDTO userData, HttpServletRequest request, HttpServletResponse response) {
		try {
			UsernamePasswordAuthenticationToken authToken =
					new UsernamePasswordAuthenticationToken(userData.getUsername(),userData.getPassword());
		
			Authentication auth = authManager.authenticate(authToken);
			
			SecurityContextHolder.getContext().setAuthentication(auth);
			
			rememberMeServices.loginSuccess(request, response, auth);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Incorrect username or password.");
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserDTO userData) {
		if(userRepository.findByUsername(userData.getUsername())!=null){
			throw new UsernameAlreadyExistsException();
		}
	
		String encryptPassword = new BCryptPasswordEncoder().encode(userData.getPassword());
		User user = new User(userData.getUsername(),encryptPassword, "USER");
		
		userRepository.save(user);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth!=null) {
			SecurityContextHolder.clearContext();
			rememberMeServices.loginFail(request, response);
		}
		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping("/user")
	public static User getUserFromSession() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    try {
	    	return (User)authentication.getPrincipal();
	    }catch(Exception e){
	    	return new User("Anonymous","123","VISITOR");
	    }
    }
	
	public static boolean isAuthorized(Post post) {
		Long postUserId = post.getUserId();
		Long currentUserId = getUserFromSession().getId();
		return postUserId == currentUserId;
	}
}

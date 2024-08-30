package com.dailypost.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailypost.exceptions.UnauthorizedAccessExceptionMessage;
import com.dailypost.models.Post;
import com.dailypost.models.User;
import com.dailypost.repositories.UserRepository;
import com.dailypost.services.PostService;

@RestController
@RequestMapping("/restPost")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping
	public ResponseEntity<String> newPost(@RequestBody Post post) {

		User currentUser = UserController.getUserFromSession();
		if(post.getUserId()==null) {
			post.setUserId(currentUser.getId());
		}else {
			if(UserController.isAuthorized(post)) {
				throw new UnauthorizedAccessExceptionMessage();
			}
		}
		
		if(post.getUser()==null) {
			post.setUser(userRepository.findById(post.getUserId()).orElseThrow(null));
		}
		
		postService.newPost(post);
		return ResponseEntity.ok().build();
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable Long id) {
		Post post = postService.getPostById(id);
		
		if(UserController.isAuthorized(post)) {
			postService.deletePost(id);
			return ResponseEntity.ok().build();
		} else {
			throw new UnauthorizedAccessExceptionMessage();
		}
		
		
	}
	@PutMapping("/{id}")
	public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody Post newPost) {
		Post post = postService.getPostById(id);
		
		if(UserController.isAuthorized(post)) {
			postService.updatePost(id, newPost);
		} else {
			throw new UnauthorizedAccessExceptionMessage();
		}
		
		return ResponseEntity.ok().build();
	}
	
}

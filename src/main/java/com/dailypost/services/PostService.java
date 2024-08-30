package com.dailypost.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dailypost.exceptions.InvalidPostException;
import com.dailypost.exceptions.PostNotFoundException;
import com.dailypost.models.Post;
import com.dailypost.repositories.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	//POST
	public ResponseEntity<String> newPost(Post post) {
		verifyPost(post);
		
		postRepository.save(post);
		return ResponseEntity.ok().build();
	}
	
	//DELETE
	public ResponseEntity<String> deletePost(Long id) {
		postRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}	
	
	//PUT
	public ResponseEntity<String> updatePost(Long id, Post newPost) {
		Post post = postRepository.findById(id).orElseThrow(()->new PostNotFoundException());
		verifyPost(newPost);
		
		post.setTitle(newPost.getTitle());
		post.setContent(newPost.getContent());
		postRepository.save(post);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	//GET**
	public List<Post> getAllPosts(){
		return postRepository.findAll();
	}
	public Post getPostById(Long id) {
		return postRepository.findById(id).orElseThrow(()->new PostNotFoundException());
	}
	public List<Post> getAllPostsByUsername(String username){
		return postRepository.findByUsername(username);
	}
	
	
	private void  verifyPost(Post post) {
		if(post.getTitle()==null || post.getTitle().isEmpty()) {
			throw new InvalidPostException("Title is required.");
		}
		if(post.getContent()==null || post.getContent().isEmpty()) {
			throw new InvalidPostException("Content is required.");
		}
	}
}

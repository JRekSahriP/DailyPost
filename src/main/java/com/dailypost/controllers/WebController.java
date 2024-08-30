package com.dailypost.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.dailypost.exceptions.UnauthorizedAccessExceptionPage;
import com.dailypost.models.Post;
import com.dailypost.models.User;
import com.dailypost.services.PostService;

@Controller
public class WebController {
		
	@Autowired
	private PostService postService;
	
	
	@GetMapping("/mainpage")
	public ModelAndView mainPage() {
		ModelAndView mav = new ModelAndView("/visitor/mainPage");
		mav.addObject("posts", postService.getAllPosts());
		mav.addObject("user", UserController.getUserFromSession());
		return mav;
	}
	
	
	@GetMapping("/post/{id}")
	public ModelAndView postPage(@PathVariable Long id) {
		ModelAndView mav = new ModelAndView("/visitor/postPage");
		Post post = postService.getPostById(id);
		User postUser = post.getUser();
		mav.addObject("post", post);
		mav.addObject("user", postUser);
		return mav;
	}
	
	@GetMapping("/post/new")
	public ModelAndView createPost() {
		return new ModelAndView("/user/createPost");
	}
	@GetMapping("/post/edit/{id}")
	public ModelAndView editPost(@PathVariable Long id) {
		if(UserController.isAuthorized(postService.getPostById(id))) {
			ModelAndView mav = new ModelAndView("/user/editPost");
			mav.addObject("post",postService.getPostById(id));
			return mav;
		} else {
			throw new UnauthorizedAccessExceptionPage();
		}
	}
	

	@GetMapping("/user/page")
	public ModelAndView userPage() {
		User user = UserController.getUserFromSession();
		ModelAndView mav = new ModelAndView("/user/userPage");
		mav.addObject("user",user);
		mav.addObject("posts",postService.getAllPostsByUsername(user.getUsername()));
		return mav;
	}
	
	@GetMapping("/user/login")
	public ModelAndView loginUser() {
		return new ModelAndView("/visitor/userLogin");
	}
	
	@GetMapping("/user/register")
	public ModelAndView registerUser() {
		return new ModelAndView("/visitor/userRegister");
	}
	@GetMapping("/user/register-success")
	public ModelAndView registerSuccess() {
		return new ModelAndView("/visitor/registerSuccess");
	}
	@GetMapping("/user/login-success")
	public ModelAndView loginSuccess() {
		ModelAndView mav = new ModelAndView("/user/loginSuccess");
		mav.addObject("Username", UserController.getUserFromSession().getUsername());
		return mav;
	}
	
	public static ModelAndView errorPage(HttpStatus error) {
    	ModelAndView mav = new ModelAndView("/visitor/errorPage");
    	mav.addObject("errorId", error.value());
    	mav.addObject("message", error.toString());
    	return mav;
    }
	public static ModelAndView errorPage(HttpStatus error, String message) {
    	ModelAndView mav = new ModelAndView("/visitor/errorPage");
    	mav.addObject("errorId", error.value());
    	mav.addObject("message", message);
    	return mav;
    }
	
}

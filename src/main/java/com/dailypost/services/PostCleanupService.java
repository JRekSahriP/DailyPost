package com.dailypost.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dailypost.repositories.PostRepository;

@Service
public class PostCleanupService {

	@Autowired
	private PostRepository postRepository;
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteAllPosts() {
		postRepository.deleteAll();
	}
	
}

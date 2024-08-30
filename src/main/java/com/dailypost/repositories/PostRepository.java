package com.dailypost.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dailypost.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{
	
	@Query("SELECT p FROM Post p WHERE p.user.username = :username")
	List<Post> findByUsername(@Param("username") String username);
}

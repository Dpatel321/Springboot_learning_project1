package com.designpoint.project1.repositoris;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.designpoint.project1.entities.Category;
import com.designpoint.project1.entities.Post;
import com.designpoint.project1.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post>findByUser(User user);
	List<Post> findByCategory(Category category);
	
	
	
	
	@Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :key, '%'))")
	List<Post> searchByTitle(@Param("key") String title);



	
}

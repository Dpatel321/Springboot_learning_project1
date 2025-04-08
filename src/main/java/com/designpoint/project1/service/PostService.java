package com.designpoint.project1.service;

import java.util.List;

import com.designpoint.project1.entities.Post;
import com.designpoint.project1.paylods.PostDto;
import com.designpoint.project1.paylods.PostResponse;

public interface PostService {

	// create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

	// update
	PostDto updatePost(PostDto postDto, Integer postId);

	// delete
	void deletePost(Integer postId);

	// get all posts
	PostResponse getAllpost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	// get single post
	PostDto getPostById(Integer postId);
	
	//get all posts by category
	List<PostDto>getPostByCategory(Integer categoryId);
	
	//get all posts by user
	List<PostDto>getPostByUser(Integer userId);
	
	
	//search posts
	List<PostDto>searchPosts(String keyword);



}

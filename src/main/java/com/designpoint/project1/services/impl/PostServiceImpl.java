package com.designpoint.project1.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.designpoint.project1.entities.Category;
import com.designpoint.project1.entities.Comment;
import com.designpoint.project1.entities.Post;
import com.designpoint.project1.entities.User;
import com.designpoint.project1.exceptions.ResourceNotFoundException;
import com.designpoint.project1.paylods.CommentDto;
import com.designpoint.project1.paylods.PostDto;
import com.designpoint.project1.paylods.PostResponse;
import com.designpoint.project1.repositoris.CategoryRepo;
import com.designpoint.project1.repositoris.PostRepo;
import com.designpoint.project1.repositoris.UserRepo;
import com.designpoint.project1.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(cat);

		Post newPost = this.postRepo.save(post);

		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "post Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());

		Post updatedpost = this.postRepo.save(post);
		return this.modelMapper.map(updatedpost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		this.postRepo.delete(post);

	}

	@Override
	public PostResponse getAllpost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(pageable);

		List<PostDto> postDtos = pagePost.getContent().stream().map(post -> this.modelMapper.map(post, PostDto.class))

				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

		System.out.println(post);
		System.out.println(post.getContent());
		// Skip automatic comment mapping
		modelMapper.typeMap(Post.class, PostDto.class).addMappings(mapper -> {
			mapper.skip(PostDto::setComments);
		});

		PostDto postDto = modelMapper.map(post, PostDto.class);

		// Safely copy the comments to avoid lazy loading/concurrent modification issues
		List<Comment> safeCommentList = new ArrayList<>(post.getComments());

		Set<CommentDto> commentDtos = safeCommentList.stream()
				.map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toSet());

		postDto.setComments(commentDtos);
		return postDto;
//	    return null;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)) // ✅ Fixed
																											// mapping
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		List<Post> posts = this.postRepo.findByUser(user);

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)) // ✅ Fixed
																											// mapping
				.collect(Collectors.toList());
		return postDtos;

	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.searchByTitle(keyword); // No need to add "%"+keyword+"%"

		// ✅ Fix incorrect mapping: Use `post`, not `posts`
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

}

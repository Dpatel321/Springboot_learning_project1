package com.designpoint.project1.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.designpoint.project1.entities.Comment;
import com.designpoint.project1.entities.Post;
import com.designpoint.project1.exceptions.ResourceNotFoundException;
import com.designpoint.project1.paylods.CommentDto;
import com.designpoint.project1.repositoris.CommentRepo;
import com.designpoint.project1.repositoris.PostRepo;
import com.designpoint.project1.service.CommentService;



@Service
public class CommentServiceimpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id", postId));
		Comment comment = this.modelMapper.map(commentDto,Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment,CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","commentId", commentId));
		this.commentRepo.delete(com);

	}

}

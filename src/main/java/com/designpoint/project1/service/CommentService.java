package com.designpoint.project1.service;



import com.designpoint.project1.paylods.CommentDto;



public interface CommentService {
	
	
	CommentDto createComment(CommentDto commentDto,Integer postId);
	void deleteComment(Integer commentId);
	

}

package com.designpoint.project1.repositoris;

import org.springframework.data.jpa.repository.JpaRepository;

import com.designpoint.project1.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}

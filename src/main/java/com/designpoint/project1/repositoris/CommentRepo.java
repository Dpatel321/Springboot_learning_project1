package com.designpoint.project1.repositoris;

import com.designpoint.project1.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}

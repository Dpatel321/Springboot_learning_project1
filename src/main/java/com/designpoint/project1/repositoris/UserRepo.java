package com.designpoint.project1.repositoris;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.designpoint.project1.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
 
}

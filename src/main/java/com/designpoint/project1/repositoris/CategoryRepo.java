package com.designpoint.project1.repositoris;

import org.springframework.data.jpa.repository.JpaRepository;

import com.designpoint.project1.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}

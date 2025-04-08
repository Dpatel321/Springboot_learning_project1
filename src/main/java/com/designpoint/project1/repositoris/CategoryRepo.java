package com.designpoint.project1.repositoris;

import com.designpoint.project1.entities.Category;


import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}

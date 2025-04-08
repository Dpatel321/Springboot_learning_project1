package com.designpoint.project1.service;

import java.util.List;

import com.designpoint.project1.paylods.CategoryDto;

public interface CategoryService {

	// create
	CategoryDto createCategory(CategoryDto categoryDto);

	// update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryID);

	// delete
	void deleteCategory(Integer categoryID);

	// get
	CategoryDto getCategory(Integer catrgoryID);

	// get All
	List<CategoryDto> getCategory();
}

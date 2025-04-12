package com.designpoint.project1.services.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.designpoint.project1.entities.Category;
import com.designpoint.project1.exceptions.ResourceNotFoundException;
import com.designpoint.project1.paylods.CategoryDto;
import com.designpoint.project1.repositoris.CategoryRepo;
import com.designpoint.project1.service.CategoryService;


@Service
public class CetegoryServiceimpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addedCat = this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryID) {
		Category cat = this.categoryRepo.findById(categoryID)
				.orElseThrow(() -> new ResourceNotFoundException("catrgoy", "categoy Id", categoryID));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());

		Category updatedcat = this.categoryRepo.save(cat);
		return this.modelMapper.map(updatedcat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryID) {
		Category cat = this.categoryRepo.findById(categoryID)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryID));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategory() {
		List<Category> category = this.categoryRepo.findAll();

	List<CategoryDto>categoryDtos =	category.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return categoryDtos;
	}

}

package com.thiendz.j6.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thiendz.j6.dao.CategoryDAO;
import com.thiendz.j6.entity.Category;
import com.thiendz.j6.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDAO categoryDAO;

	@Override
	public Category save(Category category) {
		return categoryDAO.save(category);
	}

	@Override
	public Category findById(int id) {
		Optional<Category> option = categoryDAO.findById(id);
		return option.isPresent() ? option.get() : null;
	}

	@Override
	public List<Category> findAll(Pageable pageable) {
		Page<Category> pageCategory = categoryDAO.findAll(pageable);
		return pageCategory.toList();
	}

	@Override
	public List<Category> findAllActive() {
		return categoryDAO.findAllActive();
	}
}

package com.thiendz.j6.service;



import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thiendz.j6.entity.Category;

@Service
public interface CategoryService {
	Category findById(int id);
	Category save(Category category);
	List<Category> findAll(Pageable pageable);
	List<Category> findAllActive();
}

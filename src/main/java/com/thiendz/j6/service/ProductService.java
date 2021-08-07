package com.thiendz.j6.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thiendz.j6.entity.Product;

@Service
public interface ProductService {

	Product save(Product product);

	Product findById(int id);

	List<Product> findAll(Pageable pageable);
	
	List<Product> filterByIdCategory(int idCategory);
	List<Product> filterByIdCategoryNew(int idCategory, Pageable pageable);
}

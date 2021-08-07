package com.thiendz.j6.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thiendz.j6.dao.ProductDAO;
import com.thiendz.j6.entity.Product;
import com.thiendz.j6.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDAO productDAO;

	@Override
	public Product save(Product product) {
		return productDAO.save(product);
	}

	@Override
	public Product findById(int id) {
		Optional<Product> option = productDAO.findById(id);
		return option.isPresent() ? option.get() : null;
	}

	@Override
	public List<Product> findAll(Pageable pageable) {
		Page<Product> pageProduct = productDAO.findAll(pageable);
		return pageProduct.toList();
	}

	@Override
	public List<Product> filterByIdCategory(int idCategory) {
		return productDAO.filterByIdCategory(idCategory);
	}

	@Override
	public List<Product> filterByIdCategoryNew(int idCategory, Pageable pageable) {
		return productDAO.filterByIdCategoryNew(idCategory, pageable);
	}
}

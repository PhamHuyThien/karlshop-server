package com.thiendz.j6.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.thiendz.j6.entity.Product;

@Service
public interface ProductDAO extends JpaRepository<Product, Integer>{
	@Query("SELECT p FROM Product p WHERE p.category.id = ?1 AND p.status=true AND p.category.status=true")
	List<Product> filterByIdCategory(int idCategory);
	
	@Query("SELECT p FROM Product p WHERE p.category.id = ?1 AND p.status=true AND p.category.status=true")
	List<Product> filterByIdCategoryNew(int idCategory, Pageable pageable);
}

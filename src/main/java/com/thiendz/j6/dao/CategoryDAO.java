package com.thiendz.j6.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.thiendz.j6.entity.Category;

@Service
public interface CategoryDAO extends JpaRepository<Category, Integer> {
	@Query("SELECT c FROM Category c WHERE c.status=true")
	List<Category> findAllActive();
}

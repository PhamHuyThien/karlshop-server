package com.thiendz.j6.controller.rest.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiendz.j6.dto.ResponseDTO;
import com.thiendz.j6.entity.Category;
import com.thiendz.j6.entity.Product;
import com.thiendz.j6.service.CategoryService;
import com.thiendz.j6.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/category")
public class CategoryRestController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;

	@GetMapping("/all")
	public ResponseDTO<List<Category>> getListCategory() {
		ResponseDTO<List<Category>> responeDTO = new ResponseDTO<>();
		List<Category> listCategories = categoryService.findAllActive();
		responeDTO.setStatus(1);
		responeDTO.setMessage("Lấy danh sách danh mục thành công!");
		responeDTO.setData(listCategories);
		return responeDTO;
	}

	@GetMapping("/{idCategory}/product/all")
	public ResponseDTO<List<Product>> getListProduct(
			@PathVariable(name = "idCategory", required = true) int idCategory) {
		ResponseDTO<List<Product>> responseDTO = new ResponseDTO<>();
		List<Product> listProducts = productService.filterByIdCategory(idCategory); 
		responseDTO.setStatus(1);
		responseDTO.setMessage("Lấy danh sách sản phẩm thành công!");
		responseDTO.setData(listProducts);
		return responseDTO;
	}
	
	@GetMapping("/{idCategory}/product/new")
	public ResponseDTO<List<Product>> getListProductNew(
			@PathVariable(name = "idCategory", required = true) int idCategory) {
		ResponseDTO<List<Product>> responseDTO = new ResponseDTO<>();
		final int LIMIT = 6;
		Pageable pageable = PageRequest.of(0, LIMIT, Direction.DESC, "time");
		List<Product> listProducts = productService.filterByIdCategoryNew(idCategory, pageable); 
		responseDTO.setStatus(1);
		responseDTO.setMessage("Lấy danh sách sản phẩm thành công!");
		responseDTO.setData(listProducts);
		return responseDTO;
	}
}

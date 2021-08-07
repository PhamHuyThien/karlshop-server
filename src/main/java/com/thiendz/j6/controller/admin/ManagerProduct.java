package com.thiendz.j6.controller.admin;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.thiendz.j6.dto.ProductDTO;
import com.thiendz.j6.dto.ResponseDTO;
import com.thiendz.j6.entity.Category;
import com.thiendz.j6.entity.Product;
import com.thiendz.j6.service.CategoryService;
import com.thiendz.j6.service.ProductService;
import com.thiendz.j6.utils.FileUtils;

@Controller
@RequestMapping("/admin/manager-product")
public class ManagerProduct {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;

	@GetMapping
	public void index(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "col", defaultValue = "id") String col,
			@RequestParam(name = "type", defaultValue = "DESC") String type,
			@RequestParam(name = "edit", defaultValue = "-1") int edit) {
		//
		ProductDTO productDTO = new ProductDTO();
		if (edit != -1) {
			Product product = productService.findById(edit);
			if (product != null) {
				productDTO = new ProductDTO(product);
			}
		}
		model.addAttribute(productDTO);
		//
		model.addAttribute("listCategories", categoryService.findAllActive());
		//
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(type), col);
		model.addAttribute("listProducts", productService.findAll(pageable));
		//
		model.addAttribute("edit", edit);
		model.addAttribute("type", type.equals("DESC") ? "ASC" : "DESC");
		model.addAttribute("col", col);
		model.addAttribute("size", size);
		model.addAttribute("page", page);
	}

	@PostMapping(params = "add")
	public void add(Model model, @RequestParam("image") MultipartFile image,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@Valid @ModelAttribute("productDTO") ProductDTO productDTO, BindingResult bindingResult) {
		final String PATH_SAVE = "\\img\\products";
		final String FILENAME_SAVE = FileUtils.randomName();
		model.addAttribute("page", page);
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach((v)->System.out.println(v));
			model.addAttribute("error", "Bạn phải nhập đày đủ thông tin trước!");
			return;
		}
		String type = "Thêm mới";
		if (productDTO.getId() != null) {
			type = "Chỉnh sửa";
		}
		Category category = categoryService.findById(productDTO.getIdCategory());
		if (category == null) {
			model.addAttribute("error", "Danh mục không tồn tại!");
			return;
		}
		ResponseDTO<String> resultModel = FileUtils.saveStaticImage(image, PATH_SAVE, FILENAME_SAVE);
		if (resultModel.getStatus() != 1) {
			model.addAttribute("error", resultModel.getMessage());
			return;
		}
		Product product = new Product(productDTO);
		product.setImg(resultModel.getData());
		product.setCategory(category);
		product.setTime(new Date());
		boolean error = productService.save(product) == null;
		type = type + " " + (error ? "thất bại!" : "thành công!");
		model.addAttribute(error ? "error" : "success", type);
	}
}

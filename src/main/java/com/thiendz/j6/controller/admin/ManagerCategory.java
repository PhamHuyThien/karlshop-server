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

import com.thiendz.j6.dto.CategoryDTO;
import com.thiendz.j6.entity.Category;
import com.thiendz.j6.service.CategoryService;

@Controller
@RequestMapping("/admin/manager-category")
public class ManagerCategory {
	@Autowired
	CategoryService categoryService;

	@GetMapping
	public void index(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "col", defaultValue = "id") String col,
			@RequestParam(name = "type", defaultValue = "DESC") String type,
			@RequestParam(name = "edit", defaultValue = "-1") int edit) {
		//
		CategoryDTO categoryDTO = new CategoryDTO();
		if (edit != -1) {
			Category category = categoryService.findById(edit);
			if (category != null) {
				categoryDTO = new CategoryDTO(category);
			}
		}
		model.addAttribute(categoryDTO);
		//
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(type), col);
		model.addAttribute("listCategories", categoryService.findAll(pageable));
		//
		model.addAttribute("edit", edit);
		model.addAttribute("type", type.equals("DESC") ? "ASC" : "DESC");
		model.addAttribute("col", col);
		model.addAttribute("size", size);
		model.addAttribute("page", page);
	}

	@PostMapping(params = "add")
	public void add(Model model, @Valid @ModelAttribute("categoryDTO") CategoryDTO categoryDTO,
			BindingResult bindingResult, @RequestParam(name = "page", defaultValue = "0") int page) {
		model.addAttribute("page", page);
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "Bạn phải nhập đày đủ thông tin trước!");
			return;
		}
		String type = "Thêm mới";
		if (categoryDTO.getId() != null) {
			type = "Chỉnh sửa";
		}
		Category category = new Category(categoryDTO);
		category.setTime(new Date());
		boolean error = categoryService.save(category) == null;
		type = type + " " + (error ? "thất bại!" : "thành công!");
		model.addAttribute(error ? "error" : "success", type);
	}
}

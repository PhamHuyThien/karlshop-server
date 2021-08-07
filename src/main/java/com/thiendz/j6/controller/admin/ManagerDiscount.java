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

import com.thiendz.j6.dto.DiscountDTO;
import com.thiendz.j6.entity.Discount;
import com.thiendz.j6.service.DiscountService;
import com.thiendz.j6.utils.Utils;

@Controller
@RequestMapping("/admin/manager-discount")
public class ManagerDiscount {
	@Autowired
	DiscountService discountService;

	@GetMapping
	public void index(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "col", defaultValue = "id") String col,
			@RequestParam(name = "type", defaultValue = "DESC") String type,
			@RequestParam(name = "edit", defaultValue = "-1") int edit) {
		//
		String strTimeExpired = Utils.toStringJavascriptDate(new Date());
		DiscountDTO discountDTO = new DiscountDTO();
		discountDTO.setUsages(1);
		if (edit != -1) {
			Discount discount = discountService.findById(edit);
			if (discount != null) {
				discountDTO = new DiscountDTO(discount);
				strTimeExpired = Utils.toStringJavascriptDate(discount.getTimeExpired());
			}
		}
		model.addAttribute(discountDTO);
		//
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(type), col);
		model.addAttribute("listDiscounts", discountService.findAll(pageable));
		//
		model.addAttribute("strJsDatePresent", strTimeExpired);
		model.addAttribute("edit", edit);
		model.addAttribute("type", type.equals("DESC") ? "ASC" : "DESC");
		model.addAttribute("col", col);
		model.addAttribute("size", size);
		model.addAttribute("page", page);
	}

	@PostMapping(params = "add")
	public void add(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam("dateExpired") String timeExpired,
			@Valid @ModelAttribute("discountDTO") DiscountDTO discountDTO, BindingResult bindingResult) {
		model.addAttribute("page", page);
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(System.out::println);
			model.addAttribute("error", "Bạn phải nhập đày đủ thông tin trước!");
			return;
		}
		Date dateExpired = Utils.toDate(timeExpired);
		if (dateExpired == null) {
			model.addAttribute("error", "Thời gian không hợp lệ!");
			return;
		}
		String type = "Thêm mới";
		if (discountDTO.getId() != null) {
			type = "Chỉnh sửa";
		}
		Discount discount = new Discount(discountDTO);
		discount.setCode(discount.getCode().toUpperCase());
		discount.setTimeExpired(dateExpired);
		discount.setTime(new Date());
		boolean error = discountService.save(discount) == null;
		type = type + " " + (error ? "thất bại!" : "thành công!");
		model.addAttribute(error ? "error" : "success", type);
	}
}

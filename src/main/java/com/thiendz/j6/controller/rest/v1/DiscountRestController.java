package com.thiendz.j6.controller.rest.v1;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiendz.j6.dto.DiscountCheckDTO;
import com.thiendz.j6.dto.DiscountRespDTO;
import com.thiendz.j6.dto.ResponseDTO;
import com.thiendz.j6.service.DiscountService;
import com.thiendz.j6.utils.FormUtils;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/discount")
public class DiscountRestController {
	@Autowired
	DiscountService discountService;
	
	@PostMapping("/check")
	public ResponseDTO<DiscountRespDTO> checkDiscount(@Valid @RequestBody DiscountCheckDTO discountCheckDTO, BindingResult bind){
		ResponseDTO<DiscountRespDTO> responseDTO = new ResponseDTO<>();
		if(bind.hasErrors()) {
			responseDTO.setMessage(FormUtils.toStringBindResultValid(bind));
			return responseDTO;
		}
		return discountService.check(discountCheckDTO);
	}
}

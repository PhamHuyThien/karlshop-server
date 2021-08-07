package com.thiendz.j6.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thiendz.j6.dao.DiscountDAO;
import com.thiendz.j6.dto.DiscountCheckDTO;
import com.thiendz.j6.dto.DiscountRespDTO;
import com.thiendz.j6.dto.ResponseDTO;
import com.thiendz.j6.entity.Discount;
import com.thiendz.j6.entity.Product;
import com.thiendz.j6.service.DiscountService;
import com.thiendz.j6.service.ProductService;

@Service
public class DiscountServiceImpl implements DiscountService {

	@Autowired
	DiscountDAO discountDAO;
	@Autowired
	ProductService productService;

	@Override
	public Discount save(Discount discount) {
		return discountDAO.save(discount);
	}

	@Override
	public Discount findById(int id) {
		Optional<Discount> option = discountDAO.findById(id);
		return option.isPresent() ? option.get() : null;
	}

	@Override
	public Discount findByDiscount(String discount) {
		return discountDAO.findByDiscount(discount);
	}

	@Override
	public Discount findByDiscountActive(String discount) {
		return discountDAO.findByDiscountActive(discount);
	}

	@Override
	public List<Discount> findAll(Pageable pageable) {
		Page<Discount> pageDiscount = discountDAO.findAll(pageable);
		return pageDiscount.toList();
	}

	@Override
	public ResponseDTO<DiscountRespDTO> check(DiscountCheckDTO discountCheckDTO) {
		ResponseDTO<DiscountRespDTO> responseDTO = new ResponseDTO<>();
		DiscountRespDTO discountRespDTO = new DiscountRespDTO();
		List<Product> listProductApplyDiscount = new ArrayList<>();
		//

		Discount discount = findByDiscountActive(discountCheckDTO.getCode());
		if (discount == null) {
			responseDTO.setMessage("Mã giảm giá không tồn tại!");
			return responseDTO;
		}
		//
		if (new Date().getTime() > discount.getTimeExpired().getTime()) {
			responseDTO.setMessage("Mã giảm giá đã hết hạn!");
			return responseDTO;
		}
		//
		if (discount.getUsages() < 1) {
			responseDTO.setMessage("Mã giảm giá đã được sử dụng hết!");
			return responseDTO;
		}
		discountCheckDTO.getListIdProductBuy().forEach((id) -> {
			Product product = productService.findById(id);
			if (product == null) {
				return;
			}
			// kiểm tra những sản phẩm áp dụng
			final String pattern = discount.getPattern();
			if (!pattern.equals("*")) {
				String[] procApplys = pattern.split("\\|");
				boolean match = false;
				for (String procApply : procApplys) {
					int idpr = Integer.parseInt(procApply.substring(1));
					if (procApply.startsWith("@")) {
						if (product.getCategory().getId() == idpr) {
							match = true;
							break;
						}
					} else if (procApply.startsWith("#")) {
						if (product.getId() == idpr) {
							match = true;
							break;
						}
					}
				}
				if (!match) {
					return;
				}
			}
			listProductApplyDiscount.add(product);
		});
		int totalMoney = listProductApplyDiscount.stream().map(pro-> pro.getPrice()).reduce(0, (sub, elm)-> sub + elm);
		discountRespDTO.setListProductApplyDiscount(listProductApplyDiscount);
		discountRespDTO.setIntoMoney(discountMoney(totalMoney, discount));
		responseDTO.setData(discountRespDTO);
		return responseDTO;
	}

	private int discountMoney(int totalMoney, Discount discount) {
		final String dis = discount.getDiscount();
		int disc = 0;
		if (dis.endsWith("%")) {
			int rate = Integer.parseInt(dis.substring(0, dis.length() - 1));
			disc = totalMoney * rate / 100;
		} else {
			int mon = Integer.parseInt(dis);
			disc = mon;
		}
//		int presentMoney = totalMoney - disc;
//		return presentMoney < 0 ? 0 : presentMoney;
		return disc;
	}

}

package com.thiendz.j6.controller.rest.v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiendz.j6.dto.OrderProductDTO;
import com.thiendz.j6.dto.ResponseDTO;
import com.thiendz.j6.entity.Account;
import com.thiendz.j6.entity.Discount;
import com.thiendz.j6.entity.Order;
import com.thiendz.j6.entity.OrderDetail;
import com.thiendz.j6.entity.Product;
import com.thiendz.j6.jwt.JwtTokenProvider;
import com.thiendz.j6.service.AccountService;
import com.thiendz.j6.service.OrderDetailService;
import com.thiendz.j6.service.OrderService;
import com.thiendz.j6.service.ProductService;
import com.thiendz.j6.utils.FormUtils;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/order")
public class OrderRestController {
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Autowired
	ProductService productService;
	@Autowired
	AccountService accountService;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderDetailService;

	@PostMapping("/add")
	public ResponseDTO<Order> addOrder(@Valid @RequestBody List<OrderProductDTO> listOrderProducts,
			BindingResult bindingResult, @RequestHeader(name = "Authorization", required = true) String token) {
		ResponseDTO<Order> responseDTO = new ResponseDTO<>();
		int idAccount = jwtTokenProvider.getUserIdFromJWT(token.substring(7));
		Account account = accountService.findById(idAccount);
		if (account == null) {
			responseDTO.setMessage("Tài khoản không tồn tại, hãy đăng nhập lại và thử lại!");
			return responseDTO;
		}
		if (bindingResult.hasErrors()) {
			responseDTO.setMessage(FormUtils.toStringBindResultValid(bindingResult));
			return responseDTO;
		}
		if (listOrderProducts.size() == 0) {
			responseDTO.setMessage("Đơn hàng này không có sản phẩm!");
			return responseDTO;
		}
		Order order = new Order();
		order.setAccount(account);
		order.setDiscount(new Discount(1));
		order.setStatus(true);
		order.setTime(new Date());
		List<OrderDetail> listOrderDetails = new ArrayList<>();
		for (int i = 0; i < listOrderProducts.size(); i++) {
			int idProduct = listOrderProducts.get(i).getId();
			String title = listOrderProducts.get(i).getTitle();
			int quantity = listOrderProducts.get(i).getAmount();
			Product product = productService.findById(idProduct);
			if (product == null) {
				String message = "[%d] %s không tồn tại trên cửa hàng!";
				responseDTO.setMessage(String.format(message, idProduct, title));
				return responseDTO;
			}
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProduct(product);
			orderDetail.setQuantity(quantity);
			orderDetail.setStatus(true);
			orderDetail.setTime(new Date());

			listOrderDetails.add(orderDetail);
		}
		order.setListOrderDetails(listOrderDetails);
		order = orderService.save(order);
		if (order == null) {
			responseDTO.setMessage("Tạo đơn hàng thất bại hãy thử lại!");
			return responseDTO;
		}
		for (int i = 0; i < listOrderDetails.size(); i++) {
			OrderDetail orderDetail = listOrderDetails.get(i);
			orderDetail.setOrder(order);
			if (orderDetailService.save(orderDetail) == null) {
				responseDTO.setMessage("Thêm sản phẩm vào đơn hàng thất bại!");
				return responseDTO;
			}
		}
		responseDTO.setStatus(1);
		responseDTO.setMessage("Đặt hàng thành công!");
		responseDTO.setData(order);
		return responseDTO;
	}

}

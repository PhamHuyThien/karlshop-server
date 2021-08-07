package com.thiendz.j6.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;

public class FormUtils {

	public static String toStringBindResultValid(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> listErrors = new ArrayList<String>();
			bindingResult.getAllErrors().forEach(e -> {
				listErrors.add(e.getDefaultMessage());
			});
			return String.join("<br/>", listErrors);
		}
		return null;
	}

	public static boolean comparePwd(String pwd, String confPwd) {
		if ((pwd == null && confPwd != null) || (pwd != null && confPwd == null)) {
			return false;
		}
		if (pwd == null && confPwd == null)
			return true;
		return pwd.equals(confPwd);
	}
}

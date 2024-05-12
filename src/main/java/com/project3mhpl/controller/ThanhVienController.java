/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.project3mhpl.dto.ChangePasswordDto;
import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.service.ThanhVienService;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Nguyen Nhat Vuong
 */
@Controller
public class ThanhVienController {
	@Autowired
	private ThanhVienService thanhvienService;

	@GetMapping("/profile")
	public String getProfile(Model m, HttpServletRequest request) {
		Boolean isAuthenticated = thanhvienService.checkAuth(request);

		m.addAttribute("isAuthenticated", isAuthenticated);
		if (isAuthenticated == false) {
			return "redirect:sign-in";
		}

		m.addAttribute("data", thanhvienService.getProfile(request));

		return "profile";
	}

	@GetMapping("/change-password")
	public String getChangePasswordView(Model m, HttpServletRequest request) {
		Boolean isAuthenticated = thanhvienService.checkAuth(request);

		m.addAttribute("isAuthenticated", isAuthenticated);
		if (isAuthenticated == false) {
			return "redirect:sign-in";
		}

		ChangePasswordDto form = new ChangePasswordDto();

		m.addAttribute("data", thanhvienService.getProfile(request));
		m.addAttribute("formPassword", form);

		return "change-password";
	}

	@PostMapping("/change-password")
	public String changePassword(Model m, ChangePasswordDto form, HttpServletRequest request) {
		Boolean isAuthenticated = thanhvienService.checkAuth(request);

		m.addAttribute("isAuthenticated", isAuthenticated);
		if (isAuthenticated == false) {
			return "redirect:sign-in";
		}

		ThanhVien member = thanhvienService.getProfile(request);

		m.addAttribute("data", member);
		m.addAttribute("formPassword", form);

		if (!form.newPassword.equals(form.confirmPassword)) {
			m.addAttribute("errorMessage", "Mật khẩu xác nhận không khớp với mật khẩu mới");
			return "change-password";
		}

		if (!thanhvienService.verifyUser(member.getMaTV(), form.password)) {
			m.addAttribute("errorMessage", "Mật khẩu cũ không đúng");
			return "change-password";
		}

		member.setPassword(form.confirmPassword);

		thanhvienService.saveTV(member);

		return "profile";
	}
}

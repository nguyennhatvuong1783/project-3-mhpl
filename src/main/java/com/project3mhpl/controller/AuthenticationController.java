/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project3mhpl.dto.ResetPasswordDto;
import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.service.ThanhVienService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author nhan1
 */
@Controller
public class AuthenticationController {

	@Autowired
	private ThanhVienService thanhVienService;

	@GetMapping("/sign-in")
	public String signIn(Model m, HttpServletRequest request) {

		if (thanhVienService.checkAuth(request)) {

			if (thanhVienService.checkAdmin(request)) {
				return "redirect:dashboard";
			}

			return "redirect:profile";
		}

		m.addAttribute("maTV", "");
		m.addAttribute("password", "");
		m.addAttribute("errorMessage", "");

		return "sign-in";

	}

	@GetMapping("/log-out")
	public String logout(HttpServletResponse response) {
		Cookie cookie = thanhVienService.clearAuthSession();

		response.addCookie(cookie);

		return "redirect:home";
	}

	@PostMapping(value = "/login", consumes = { "application/x-www-form-urlencoded" })
	public String login(Model m, ThanhVien loginForm, HttpServletResponse response) {

		boolean isLoggedIn = thanhVienService.verifyUser(loginForm.getMaTV(), loginForm.getPassword());

		if (isLoggedIn) {
			Cookie cookie = thanhVienService.createAuthSession(loginForm.getMaTV().toString());

			response.addCookie(cookie);

			if (thanhVienService.isAdminUser(loginForm.getMaTV())) {
				return "redirect:dashboard";
			}

			return "redirect:profile";
		}

		m.addAttribute("errorMessage", "Sai mật khẩu hoặc tài khoản không tồn tại");

		return "sign-in";
	}

	@GetMapping("/sign-up")
	public String b(Model m) {
		ThanhVien tv = new ThanhVien();
		m.addAttribute("thanhvien", tv);

		return "sign-up";
	}

	@PostMapping("/sign-up")
	public String processRegistration(@ModelAttribute("ThanhVien") ThanhVien tv, Model m, ThanhVien form) {
		if (tv.getMaTV().toString().length() != 10 || !tv.getMaTV().toString().matches("\\d+")) {
			m.addAttribute("errorMessage", "Mã thành viên đủ 10 số và không chứa kí tự!");
			m.addAttribute("thanhvien", tv);
			return "sign-up";
		}
		if (!tv.getHoten().matches("^[\\p{L}\\s]+$")) {
			m.addAttribute("errorMessage", "Tên thành viên chỉ chứa chữ cái!");
			m.addAttribute("thanhvien", tv);
			return "sign-up";
		}
		if (!tv.getKhoa().matches("^[\\p{L}\\s]+$") || !tv.getNganh().matches("^[\\p{L}\\s]+$")) {
			m.addAttribute("errorMessage", "Khoa và ngành chỉ chứa chữ cái!");
			m.addAttribute("thanhvien", tv);
			return "sign-up";
		}
		if (!tv.getSdt().matches("0\\d{9}")) {
			m.addAttribute("errorMessage", "Số điện thoại không đúng dịnh dạng!");
			m.addAttribute("thanhvien", tv);
			return "sign-up";
		}
		if (!tv.getEmail().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
			m.addAttribute("errorMessage", "Email không đúng dịnh dạng!");
			m.addAttribute("thanhvien", tv);
			return "sign-up";
		}
		tv.setMaTV(Integer.parseInt(tv.getMaTV().toString()));
		tv.setIsAdmin(false);
		thanhVienService.saveTV(tv);

		return "sign-in";
	}

	@PostMapping("/reset-password")
	public String postMethodName(ResetPasswordDto user) {

		thanhVienService.resetPassword(user.email);

		return "sign-in";
	}

	@GetMapping("/reset-password")
	public String resetPasswordView() {

		return "reset-password";
	}

}

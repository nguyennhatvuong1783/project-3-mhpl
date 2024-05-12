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

			return "redirect:/profile";
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
	public String processRegistration(@ModelAttribute("ThanhVien") ThanhVien tv) {
		System.out.println(tv.toString());

		tv.setMaTV(Integer.parseInt(tv.getMaTV().toString()));

		thanhVienService.saveTV(tv);

		return "sign-in";
	}
}

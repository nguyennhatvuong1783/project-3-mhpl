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
import org.springframework.web.util.WebUtils;

import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.service.ThanhVienService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

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

		Cookie c = WebUtils.getCookie(request, "auth");

		if (c != null && c.getValue() != null && c.getValue() != "") {
			System.out.println(c.getValue());
			return "redirect:profile";
		}

		m.addAttribute("maTV", "");
		m.addAttribute("password", "");
		m.addAttribute("errorMessage", "");

		return "sign-in";

	}

	@GetMapping("/log-out")
	public String logout(HttpServletResponse response) {
		Cookie cookie = new Cookie("auth", "");
		cookie.setSecure(true);
		cookie.setHttpOnly(true);

		response.addCookie(cookie);

		return "redirect:home";
	}

	@PostMapping(value = "/login", consumes = { "application/x-www-form-urlencoded" })
	public String login(Model m, ThanhVien loginForm, HttpSession session, HttpServletResponse response) {

		boolean isLoggedIn = thanhVienService.verifyUser(loginForm.getMaTV(), loginForm.getPassword());

		if (isLoggedIn) {
			Optional<ThanhVien> tv = thanhVienService.getById(loginForm.getMaTV());
			session.setAttribute("tv", tv);

			Cookie cookie = new Cookie("auth", loginForm.getMaTV().toString());
			cookie.setSecure(true);
			cookie.setHttpOnly(true);

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

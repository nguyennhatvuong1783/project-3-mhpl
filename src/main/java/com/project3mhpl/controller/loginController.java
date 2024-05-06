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

import com.project3mhpl.dto.LoginForm;
import com.project3mhpl.service.ThanhVienService;

/**
 *
 * @author nhan1
 */
@Controller
public class LoginController {

	@Autowired
	private ThanhVienService thanhVienService;

	@GetMapping("/login")
	public String showLogin(Model m) {
		m.addAttribute("maTV", "");
		m.addAttribute("password", "");
		m.addAttribute("errorMessage", "");

		return "login";
	}

	@PostMapping(value = "/login", consumes = { "application/x-www-form-urlencoded" })
	public String login(Model m, LoginForm loginForm) {

		boolean isLoggedIn = thanhVienService.verifyUser(loginForm.getMaTV(), loginForm.getPassword());

		if (isLoggedIn) {
			return "Profile";
		}

		m.addAttribute("errorMessage", "Sai mật khẩu hoặc tài khoản không tồn tại");

		return "login";
	}
}

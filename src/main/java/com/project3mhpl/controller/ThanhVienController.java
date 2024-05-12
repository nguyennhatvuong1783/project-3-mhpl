/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;

import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.service.ThanhVienService;

import jakarta.servlet.http.Cookie;
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
		Cookie c = WebUtils.getCookie(request, "auth");

		m.addAttribute("isAuthenticated", c != null && c.getValue() != null && c.getValue() != "");
		if (c == null || c.getValue() == null) {
			return "redirect:/sign-in";
		}

		Optional<ThanhVien> authUser = thanhvienService.getById(Integer.parseInt(c.getValue()));

		ThanhVien tv = authUser.get();

		m.addAttribute("data", tv);
		m.addAttribute("isAuthenticated", tv != null);

		return "profile";
	}
}

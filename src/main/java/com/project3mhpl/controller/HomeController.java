/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Nguyen Nhat Vuong
 */
@Controller
public class HomeController {
	@GetMapping("/")
	public String index() {
		return "redirect:home";
	}

	@GetMapping("/home")
	public String homepage(Model m, HttpServletRequest request) {
		Cookie c = WebUtils.getCookie(request, "auth");

		m.addAttribute("isAuthenticated", c != null && c.getValue() != null && c.getValue() != "");

		return "index";
	}
}

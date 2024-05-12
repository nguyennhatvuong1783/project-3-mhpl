/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project3mhpl.service.ThanhVienService;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Nguyen Nhat Vuong
 */
@Controller
public class HomeController {
	private ThanhVienService thanhVienService;

	@GetMapping("/")
	public String index() {
		return "redirect:home";
	}

	@GetMapping("/home")
	public String homepage(Model m, HttpServletRequest request) {
		m.addAttribute("isAuthenticated", thanhVienService.checkAuth(request));

		return "index";
	}
        
        @GetMapping("/dashboard")
	public String dashboardPage (Model m, HttpServletRequest request) {
//		m.addAttribute("isAuthenticated", thanhVienService.checkAuth(request));

		return "dashboard";
	}
}

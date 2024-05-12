/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.project3mhpl.entity.ThietBi;
import com.project3mhpl.service.ThietBiService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Nguyen Nhat Vuong
 */
@Controller
public class ThietBiController {
	@Autowired
	private ThietBiService thietBiService;

	@GetMapping("/dat-muon-thiet-bi")
	public String getAll(Model m, HttpServletRequest request) {
		Cookie c = WebUtils.getCookie(request, "auth");

		m.addAttribute("isAuthenticated", c != null && c.getValue() != null && c.getValue() != "");
		if (c == null || c.getValue() == null || c.getValue() == "") {
			return "redirect:sign-in";
		}

		Iterable<ThietBi> list = thietBiService.getAll();
		ArrayList<ThietBi> products = new ArrayList<>();
		for (ThietBi t : list) {
			products.add(t);
		}

		m.addAttribute("data", products);
		return "thiet_bi_all";
	}

	@RequestMapping(value = "/dat-muon-thiet-bi", method = RequestMethod.POST)
	public String submitData(@RequestParam("date") String date, Model m, HttpServletRequest request) {
		Cookie c = WebUtils.getCookie(request, "auth");

		m.addAttribute("isAuthenticated", c != null && c.getValue() != null && c.getValue() != "");
		if (c == null || c.getValue() == null || c.getValue() == "") {
			return "redirect:sign-in";
		}

		// Xử lý dữ liệu và trả về view hoặc thực hiện các hành động khác
		// ...
		return "redirect:/";
	}

	@RequestMapping(value = "/muonthietbi", method = RequestMethod.POST)
	public String submitData2(@RequestParam("date") String date, Model m, HttpServletRequest request) {
		Cookie c = WebUtils.getCookie(request, "auth");

		m.addAttribute("isAuthenticated", c != null && c.getValue() != null && c.getValue() != "");
		if (c == null || c.getValue() == null || c.getValue() == "") {
			return "redirect:sign-in";
		}

		// Xử lý dữ liệu và trả về view hoặc thực hiện các hành động khác
		// ...
		return "redirect:/";
	}

}

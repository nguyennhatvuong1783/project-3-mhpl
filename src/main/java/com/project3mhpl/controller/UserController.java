/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.service.ThanhVienService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	@Autowired
	private ThanhVienService thanhvienService;

	@GetMapping("/manage-users")
	public String getProfile(Model m, HttpServletRequest request) {
		Boolean isAuthenticated = thanhvienService.checkAuth(request);

		m.addAttribute("isAuthenticated", isAuthenticated);
		if (isAuthenticated == false) {
			return "redirect:sign-in";
		}

		m.addAttribute("isAdmin", thanhvienService.checkAdmin(request));
		m.addAttribute("users", thanhvienService.getAll());

		return "manage-users";
	}

	@PostMapping("/user/import")
	public String importUsers(@RequestParam("file") MultipartFile file) throws IOException {

		thanhvienService.saveListTV(file);

		return "redirect:/dashboard#manage-users";
	}

	@PostMapping("/user/delete")
	public String deleteUser(ThanhVien tv) {

		thanhvienService.deleteTV(tv.getMaTV());

		return "redirect:/dashboard#manage-users";
	}

	// @PostMapping("/user/update/${maTV}")
	// public String updateUser(@PathVariable("maTV") Integer maTV, ThanhVien tv) {

	// hanhvienService.updateTV(maTV, tv);

	// eturn "redirect:/dashboard#manage-users";
	// }

}

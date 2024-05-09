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

import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.service.ThanhVienService;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String login(Model m, ThanhVien loginForm,HttpSession session) {

            boolean isLoggedIn = thanhVienService.verifyUser(loginForm.getMaTV(), loginForm.getPassword());

            if (isLoggedIn) {
                Optional<ThanhVien> tv= thanhVienService.getById(loginForm.getMaTV());
                session.setAttribute("tv", tv);
                return "redirect:/profile";
            }

            m.addAttribute("errorMessage", "Sai mật khẩu hoặc tài khoản không tồn tại");

            return "login";
	}
}

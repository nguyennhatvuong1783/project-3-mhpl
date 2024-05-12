/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.service;

import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.repository.ThanhVienRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author ADMIN
 */
@Service
public class ThanhVienService {
	@Autowired
	private ThanhVienRepository thanhvienRepository;

	public Iterable<ThanhVien> getAll() {
		return thanhvienRepository.findAll();
	}

	public Optional<ThanhVien> getById(Integer id) {
		return thanhvienRepository.findById(id);
	}

	public Boolean verifyUser(Integer maTV, String password) {
		Optional<ThanhVien> thanhVien = thanhvienRepository.findById(maTV);

		return thanhVien.isPresent() && thanhVien.get().getPassword().equals(password);
	}

	public Boolean saveTV(ThanhVien tv) {
		return thanhvienRepository.save(tv) != null;
	}

	public Boolean checkAuth(HttpServletRequest request) {
		Cookie c = WebUtils.getCookie(request, "auth");

		return c != null && c.getValue() != null && c.getValue() != "";
	}

	public Cookie createAuthSession(String maTV) {
		Cookie cookie = new Cookie("auth", maTV);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);

		return cookie;
	}

	public Cookie clearAuthSession() {
		Cookie cookie = new Cookie("auth", "");
		cookie.setSecure(true);
		cookie.setHttpOnly(true);

		return cookie;
	}

	@SuppressWarnings("null")
	public ThanhVien getProfile(HttpServletRequest request) {
		Cookie c = WebUtils.getCookie(request, "auth");

		Optional<ThanhVien> authUser = thanhvienRepository.findById(Integer.parseInt(c.getValue()));

		return authUser.get();

	}
}

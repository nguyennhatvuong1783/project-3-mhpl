/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.project3mhpl.entity.XuLy;
import com.project3mhpl.repository.XuLyRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author ADMIN
 */
@Service
public class XuLyService {
	@Autowired
	private XuLyRepository xulyRepository;

	public Iterable<XuLy> getAll() {
		return xulyRepository.findAll();
	}

	@SuppressWarnings("null")
	public List<XuLy> getTTXLByIdTV(HttpServletRequest request) {
		Cookie c = WebUtils.getCookie(request, "auth");
		List<XuLy> authUser = xulyRepository.findByThanhVienXL_MaTV(Integer.parseInt(c.getValue()));
		return authUser;
	}
}

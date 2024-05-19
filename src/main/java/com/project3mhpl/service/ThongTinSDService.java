/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.project3mhpl.entity.ThongTinSD;
import com.project3mhpl.repository.ThongTinSDRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Tran Nhat Qui
 */
@Service
public class ThongTinSDService {
	@Autowired
	private ThongTinSDRepository thongTinSDRepository;

	public Iterable<ThongTinSD> getAll() {
		return thongTinSDRepository.findAll();
	}

	public List<ThongTinSD> getTTSDByIdTB(Integer idTB) {
		return thongTinSDRepository.findByThietBi_MaTB(idTB);
	}

	public boolean store(ThongTinSD thongTinSD) {
		try {
			thongTinSDRepository.save(thongTinSD);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	// public List<ThongTinSD> getTTSDByIdTV(Integer idTV) {
	// return thongTinSDRepository.findByThietBi_MaTV(idTV);
	// }
	@SuppressWarnings("null")
	public List<ThongTinSD> getTTSDByIdTV(HttpServletRequest request) {
		Cookie c = WebUtils.getCookie(request, "auth");
		List<ThongTinSD> authUser = thongTinSDRepository.findByThanhVienTTSD_MaTV(Integer.parseInt(c.getValue()));
		return authUser;
	}
}

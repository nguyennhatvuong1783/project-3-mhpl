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

import com.project3mhpl.entity.ThietBi;
import com.project3mhpl.entity.ThongTinSD;
import com.project3mhpl.service.ThanhVienService;
import com.project3mhpl.service.ThietBiService;
import com.project3mhpl.service.ThongTinSDService;

import jakarta.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

/**
 *
 * @author Nguyen Nhat Vuong
 */
@Controller
public class ThietBiController {
	@Autowired
	private ThietBiService thietBiService;

	@Autowired
	private ThanhVienService thanhVienService;

	@Autowired
	private ThongTinSDService thongTinSDService;

	@GetMapping("/dat-muon-thiet-bi")
	public String getAll(Model m, HttpServletRequest request) {
		Boolean isAuthenticated = thanhVienService.checkAuth(request);

		m.addAttribute("isAuthenticated", isAuthenticated);
		if (isAuthenticated == false) {
			return "redirect:sign-in";
		}
                m.addAttribute("isAdmin", thanhVienService.checkAdmin(request));

		Iterable<ThietBi> list = thietBiService.getAll();
		ArrayList<ThietBi> products = new ArrayList<>();
		for (ThietBi t : list) {
			products.add(t);
		}

		m.addAttribute("data", products);
		return "thiet_bi_all";
	}

	@RequestMapping(value = "/dat-muon-thiet-bi", method = RequestMethod.POST)
	public String submitData(@RequestParam("date") String date, @RequestParam("idTB") int maTB, Model m,
			HttpServletRequest request) throws Exception {
		Boolean isAuthenticated = thanhVienService.checkAuth(request);

		m.addAttribute("isAuthenticated", isAuthenticated);
		if (isAuthenticated == false) {
			return "redirect:sign-in";
		}

		// Xử lý dữ liệu và trả về view hoặc thực hiện các hành động khác
		List<ThongTinSD> list = thongTinSDService.getTTSDByIdTB(maTB);
		System.out.println(list.get(0).getTgMuon());
		Calendar calendar = Calendar.getInstance();
		Date tgDatCho = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
		System.out.println("Thoi gian dat cho: " + tgDatCho);

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getTgDatcho() != null) {
				calendar.setTime(list.get(i).getTgDatcho());
				calendar.add(Calendar.HOUR_OF_DAY, 1);
				Date tgDatChoPlus1h = calendar.getTime();
				if (tgDatCho.compareTo(tgDatChoPlus1h) > 0) {
					return "modal";
				}
			} else {
				if (tgDatCho.compareTo(list.get(i).getTgTra()) > 0) {
					return "modal";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/muonthietbi", method = RequestMethod.POST)
	public String submitData2(@RequestParam("date") String date, Model m, HttpServletRequest request) {
		Boolean isAuthenticated = thanhVienService.checkAuth(request);

		m.addAttribute("isAuthenticated", isAuthenticated);
		if (isAuthenticated == false) {
			return "redirect:sign-in";
		}

		// Xử lý dữ liệu và trả về view hoặc thực hiện các hành động khác
		// ...
		return "redirect:/";
	}

        @GetMapping("/manage-devices")
	public String manageDevicesPage () {
		return "manage-devices";
	}
}

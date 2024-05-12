/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import com.project3mhpl.entity.ThanhVien;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.Optional;
import org.springframework.web.bind.annotation.ResponseBody;

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
                Date currentDate = new Date();
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
                m.addAttribute("currentDate", currentDate);
		return "thiet_bi_all";
	}

	@RequestMapping(value = "/dat-muon-thiet-bi", method = RequestMethod.POST)
        @ResponseBody
	public String submitData(@RequestParam("date") String date, @RequestParam("idTB") int maTB, Model m,
			HttpServletRequest request) throws ParseException {
		Boolean isAuthenticated = thanhVienService.checkAuth(request);

		m.addAttribute("isAuthenticated", isAuthenticated);
		if (isAuthenticated == false) {
			return "redirect:sign-in";
		}

		// Xử lý dữ liệu và trả về view hoặc thực hiện các hành động khác
		List<ThongTinSD> list = thongTinSDService.getTTSDByIdTB(maTB);
		Calendar calendar = Calendar.getInstance();
		Date tgDatCho = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getTgDatcho() != null) {
				calendar.setTime(list.get(i).getTgDatcho());
				calendar.add(Calendar.HOUR_OF_DAY, 1);
				Date tgDatChoPlus1h = calendar.getTime();
				if (tgDatCho.compareTo(tgDatChoPlus1h) <= 0) {
					return "alert";
				}
			} else {
				if (tgDatCho.compareTo(list.get(i).getTgTra()) <= 0) {
					return "alert";
				}
			}
		}
                
                Iterable<ThongTinSD> ThongTinSDIterable = thongTinSDService.getAll();
                int maTT = 0;
                for (ThongTinSD thongTinSD : ThongTinSDIterable) {
                    maTT++;
                }
                maTT++;

                ThanhVien tv = thanhVienService.getProfile(request);
                Optional<ThietBi> thietBiOptional = thietBiService.getByID(maTB);
                ThietBi thietBi = thietBiOptional.get();
                ThongTinSD thongTinSD = new ThongTinSD(maTT, tv, thietBi, tgDatCho, null, null, null);
                
                thongTinSDService.store(thongTinSD);
                
		return "modal";
	}

	@RequestMapping(value = "/muonthietbi", method = RequestMethod.POST)
        @ResponseBody
	public String submitData2(@RequestParam("date") String date, @RequestParam("idTBMuon") int maTB, Model m, HttpServletRequest request) throws ParseException {
		Boolean isAuthenticated = thanhVienService.checkAuth(request);

		m.addAttribute("isAuthenticated", isAuthenticated);
		if (isAuthenticated == false) {
			return "redirect:sign-in";
		}

		// Xử lý dữ liệu và trả về view hoặc thực hiện các hành động khác
                Date tgTra = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
                Date tgMuon = new Date();
                Iterable<ThongTinSD> ThongTinSDIterable = thongTinSDService.getAll();
                int maTT = 0;
                for (ThongTinSD thongTinSD : ThongTinSDIterable) {
                    maTT++;
                }
                maTT++;

                ThanhVien tv = thanhVienService.getProfile(request);
                Optional<ThietBi> thietBiOptional = thietBiService.getByID(maTB);
                ThietBi thietBi = thietBiOptional.get();
                ThongTinSD thongTinSD = new ThongTinSD(maTT, tv, thietBi, null, null, tgMuon, tgTra);
                
                thongTinSDService.store(thongTinSD);
                
		return "modal";
	}
        
        @RequestMapping(value = "/them-thiet-bi", method = RequestMethod.POST)
        @ResponseBody
	public String themThietBi(@RequestParam("matb") int maTB, @RequestParam("tentb") String tenTB, @RequestParam("motatb") String moTaTB, Model m, HttpServletRequest request) {
		Boolean isAuthenticated = thanhVienService.checkAuth(request);

		m.addAttribute("isAuthenticated", isAuthenticated);
		if (isAuthenticated == false) {
			return "redirect:sign-in";
		}

		// Xử lý dữ liệu và trả về view hoặc thực hiện các hành động khác
                ThietBi thietBi = new ThietBi(maTB, tenTB, moTaTB);
                
                thietBiService.store(thietBi);
                
		return "modal";
	}

        @GetMapping("/manage-devices")
	public String manageDevicesPage (Model m, HttpServletRequest request) {
            Boolean isAuthenticated = thanhVienService.checkAuth(request);
            Boolean isAdmin = thanhVienService.checkAdmin(request);

            m.addAttribute("isAuthenticated", isAuthenticated);
            if (isAuthenticated == false) {
                    return "redirect:sign-in";
            }
            m.addAttribute("isAdmin", isAdmin);
            if (isAdmin == false) {
                    return "redirect:home";
            }
            Iterable<ThietBi> list = thietBiService.getAll();
            ArrayList<ThietBi> products = new ArrayList<>();
            for (ThietBi t : list) {
                    products.add(t);
            }

            m.addAttribute("data", products);
            return "manage-devices";
	}
}

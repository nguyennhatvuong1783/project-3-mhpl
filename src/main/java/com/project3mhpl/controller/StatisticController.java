/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.entity.ThongTinSD;
import com.project3mhpl.entity.XuLy;
import com.project3mhpl.service.ThanhVienService;
import com.project3mhpl.service.ThongTinSDService;
import com.project3mhpl.service.XulyService;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Nguyen Nhat Vuong
 */
@Controller
public class StatisticController {

	@Autowired
	private ThanhVienService thanhVienService;

	@Autowired
	private ThongTinSDService thongTinSDService;

	@Autowired
	private XulyService xuLyService;

	@GetMapping("/statistic")
	public String index(Model m, HttpServletRequest request) {
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

		Iterable<ThanhVien> usersIterable = thanhVienService.getAll();
		Iterable<ThongTinSD> ttsdIterable = thongTinSDService.getAll();
		Iterable<XuLy> xuLyIterable = xuLyService.getAll();

		// Thành viên
		ArrayList<ThongTinSD> ttsdArr = new ArrayList<>();
		for (ThongTinSD ttsd : ttsdIterable) {
			if (ttsd.getTgVao() != null) {
				ttsdArr.add(ttsd);
			}
		}

		HashSet<Integer> thanhVienSet = new HashSet<>();
		for (ThongTinSD ttsd : ttsdArr) {
			thanhVienSet.add(ttsd.getThanhVienTTSD().getMaTV());
		}
		int soLuong = thanhVienSet.size();

		Set<String> uniqueKhoa = new HashSet<>();
		for (ThanhVien user : usersIterable) {
			uniqueKhoa.add(user.getKhoa());
		}

		Set<String> uniqueNganh = new HashSet<>();
		for (ThanhVien user : usersIterable) {
			uniqueNganh.add(user.getNganh());
		}

		int totalCheckIn = 0;

		for (ThanhVien tv : usersIterable) {
			if (tv.getCheckIn() != null) {
				totalCheckIn = totalCheckIn + tv.getCheckIn();
			}

		}

		m.addAttribute("totalCheckIn", totalCheckIn);
		m.addAttribute("users", usersIterable);
		m.addAttribute("soLuong", soLuong);
		m.addAttribute("nganh", uniqueNganh);
		m.addAttribute("khoa", uniqueKhoa);
		m.addAttribute("ttsd", ttsdArr);

		// Mượn thiết bị
		ArrayList<ThongTinSD> ttsdArrBorrow = new ArrayList<>();
		for (ThongTinSD ttsd : ttsdIterable) {
			if (ttsd.getTgMuon() != null && ttsd.getTgTra() != null) {
				ttsdArrBorrow.add(ttsd);
			}
		}

		m.addAttribute("ttsdBorrow", ttsdArrBorrow);
		m.addAttribute("luotMuon", ttsdArrBorrow.size());

		// Đang mượn thiết bị
		ArrayList<ThongTinSD> ttsdArrBorrowing = new ArrayList<>();
		for (ThongTinSD ttsd : ttsdIterable) {
			if (ttsd.getTgMuon() != null && ttsd.getTgTra() == null) {
				ttsdArrBorrowing.add(ttsd);
			}
		}

		m.addAttribute("ttsdBorrowing", ttsdArrBorrowing);
		m.addAttribute("luotDangMuon", ttsdArrBorrowing.size());

		// Vi phạm
		int countDa = 0;
		for (XuLy xl : xuLyIterable) {
			if (xl.getTrangThaiXL() == true) {
				countDa++;
			}
		}

		int countChua = 0;
		for (XuLy xl : xuLyIterable) {
			if (xl.getTrangThaiXL() == false) {
				countChua++;
			}
		}

		long tongTien = 0;
		for (XuLy xl : xuLyIterable) {
			if (xl.getSoTien() != null) {
				tongTien += xl.getSoTien();
			}
		}

		m.addAttribute("tongTien", tongTien);
		m.addAttribute("xuly", xuLyIterable);
		m.addAttribute("countDa", countDa);
		m.addAttribute("countChua", countChua);

		return "statistic";
	}

	@GetMapping("/filter")
	public String filter(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("khoa") String khoa, @RequestParam("nganh") String nganh, Model m, HttpServletRequest request)
			throws ParseException {
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

		// Thành viên
		Iterable<ThanhVien> usersIterable = thanhVienService.getAll();
		Iterable<ThongTinSD> ttsdIterable = thongTinSDService.getAll();
		Iterable<XuLy> xuLyIterable = xuLyService.getAll();
		ArrayList<ThongTinSD> ttsdArr = new ArrayList<>();

		for (ThongTinSD ttsd : ttsdIterable) {
			if (ttsd.getTgVao() != null) {
				ttsdArr.add(ttsd);
			}
		}

		ArrayList<ThongTinSD> ttsdArrFilter = new ArrayList<>(ttsdArr);
		for (ThongTinSD ttsd : ttsdArr) {
			if (startDate != "") {
				Date startDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startDate);
				if (startDateTime.compareTo(ttsd.getTgVao()) > 0) {
					ttsdArrFilter.remove(ttsd);
				}
			}
			if (endDate != "") {
				Date endDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endDate);
				if (endDateTime.compareTo(ttsd.getTgVao()) < 0) {
					ttsdArrFilter.remove(ttsd);
				}
			}
			if (!khoa.equals("filter")) {
				if (!khoa.equals(ttsd.getThanhVienTTSD().getKhoa())) {
					ttsdArrFilter.remove(ttsd);
				}
			}
			if (!nganh.equals("filter")) {
				if (!nganh.equals(ttsd.getThanhVienTTSD().getNganh())) {
					ttsdArrFilter.remove(ttsd);
				}
			}
		}

		HashSet<Integer> thanhVienSet = new HashSet<>();
		for (ThongTinSD ttsd : ttsdArrFilter) {
			thanhVienSet.add(ttsd.getThanhVienTTSD().getMaTV());
		}
		int soLuong = thanhVienSet.size();

		Set<String> uniqueKhoa = new HashSet<>();
		for (ThanhVien user : usersIterable) {
			uniqueKhoa.add(user.getKhoa());
		}

		Set<String> uniqueNganh = new HashSet<>();
		for (ThanhVien user : usersIterable) {
			uniqueNganh.add(user.getNganh());
		}

		int totalCheckIn = 0;

		for (ThanhVien tv : usersIterable) {
			if (tv.getCheckIn() != null) {
				totalCheckIn += tv.getCheckIn();
			}

		}

		m.addAttribute("totalCheckIn", totalCheckIn);
		m.addAttribute("users", usersIterable);
		m.addAttribute("soLuong", soLuong);
		m.addAttribute("nganh", uniqueNganh);
		m.addAttribute("khoa", uniqueKhoa);
		m.addAttribute("ttsd", ttsdArrFilter);

		// Mượn thiết bị
		ArrayList<ThongTinSD> ttsdArrBorrow = new ArrayList<>();
		for (ThongTinSD ttsd : ttsdIterable) {
			if (ttsd.getTgMuon() != null && ttsd.getTgTra() != null) {
				ttsdArrBorrow.add(ttsd);
			}
		}

		m.addAttribute("ttsdBorrow", ttsdArrBorrow);
		m.addAttribute("luotMuon", ttsdArrBorrow.size());

		// Đang mượn thiết bị
		ArrayList<ThongTinSD> ttsdArrBorrowing = new ArrayList<>();
		for (ThongTinSD ttsd : ttsdIterable) {
			if (ttsd.getTgMuon() != null && ttsd.getTgTra() == null) {
				ttsdArrBorrowing.add(ttsd);
			}
		}

		m.addAttribute("ttsdBorrowing", ttsdArrBorrowing);
		m.addAttribute("luotDangMuon", ttsdArrBorrowing.size());

		// Vi phạm
		int countDa = 0;
		for (XuLy xl : xuLyIterable) {
			if (xl.getTrangThaiXL() == true) {
				countDa++;
			}
		}

		int countChua = 0;
		for (XuLy xl : xuLyIterable) {
			if (xl.getTrangThaiXL() == false) {
				countChua++;
			}
		}

		long tongTien = 0;
		for (XuLy xl : xuLyIterable) {
			if (xl.getSoTien() != null) {
				tongTien += xl.getSoTien();
			}
		}

		m.addAttribute("tongTien", tongTien);
		m.addAttribute("xuly", xuLyIterable);
		m.addAttribute("countDa", countDa);
		m.addAttribute("countChua", countChua);

		return "statistic";
	}

	@GetMapping("/filterBorrow")
	public String filterBorrow(@RequestParam("startDateBorrow") String startDate,
			@RequestParam("endDateBorrow") String endDate, @RequestParam("devices") String device, Model m,
			HttpServletRequest request) throws ParseException {
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

		Iterable<ThanhVien> usersIterable = thanhVienService.getAll();
		Iterable<ThongTinSD> ttsdIterable = thongTinSDService.getAll();
		Iterable<XuLy> xuLyIterable = xuLyService.getAll();

		// Thành viên
		ArrayList<ThongTinSD> ttsdArr = new ArrayList<>();
		for (ThongTinSD ttsd : ttsdIterable) {
			if (ttsd.getTgVao() != null) {
				ttsdArr.add(ttsd);
			}
		}

		HashSet<Integer> thanhVienSet = new HashSet<>();
		for (ThongTinSD ttsd : ttsdArr) {
			thanhVienSet.add(ttsd.getThanhVienTTSD().getMaTV());
		}
		int soLuong = thanhVienSet.size();

		Set<String> uniqueKhoa = new HashSet<>();
		for (ThanhVien user : usersIterable) {
			uniqueKhoa.add(user.getKhoa());
		}

		Set<String> uniqueNganh = new HashSet<>();
		for (ThanhVien user : usersIterable) {
			uniqueNganh.add(user.getNganh());
		}

		int totalCheckIn = 0;
		for (ThanhVien tv : usersIterable) {
			if (tv.getCheckIn() == null) {
				tv.setCheckIn(0);
			}

			totalCheckIn += tv.getCheckIn();
		}

		m.addAttribute("totalCheckIn", totalCheckIn);
		m.addAttribute("users", usersIterable);
		m.addAttribute("soLuong", soLuong);
		m.addAttribute("nganh", uniqueNganh);
		m.addAttribute("khoa", uniqueKhoa);
		m.addAttribute("ttsd", ttsdArr);

		// Mượn thiết bị
		ArrayList<ThongTinSD> ttsdArrBorrow = new ArrayList<>();
		for (ThongTinSD ttsd : ttsdIterable) {
			if (ttsd.getTgMuon() != null && ttsd.getTgTra() != null) {
				ttsdArrBorrow.add(ttsd);
			}
		}

		ArrayList<ThongTinSD> ttsdArrBorrowFilter = new ArrayList<>(ttsdArrBorrow);
		for (ThongTinSD ttsd : ttsdArrBorrow) {
			if (startDate != "") {
				Date startDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startDate);
				if (startDateTime.compareTo(ttsd.getTgTra()) > 0) {
					ttsdArrBorrowFilter.remove(ttsd);
				}
			}
			if (endDate != "") {
				Date endDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endDate);
				if (endDateTime.compareTo(ttsd.getTgMuon()) < 0) {
					ttsdArrBorrowFilter.remove(ttsd);
				}
			}
			if (!device.equals("")) {
				if (!(ttsd.getThietBi().getTenTB()).toLowerCase().contains(device.toLowerCase())) {
					ttsdArrBorrowFilter.remove(ttsd);
				}
			}
		}

		m.addAttribute("ttsdBorrow", ttsdArrBorrowFilter);
		m.addAttribute("luotMuon", ttsdArrBorrowFilter.size());

		// Đang mượn thiết bị
		ArrayList<ThongTinSD> ttsdArrBorrowing = new ArrayList<>();
		for (ThongTinSD ttsd : ttsdIterable) {
			if (ttsd.getTgMuon() != null && ttsd.getTgTra() == null) {
				ttsdArrBorrowing.add(ttsd);
			}
		}

		m.addAttribute("ttsdBorrowing", ttsdArrBorrowing);
		m.addAttribute("luotDangMuon", ttsdArrBorrowing.size());

		// Vi phạm
		int countDa = 0;
		for (XuLy xl : xuLyIterable) {
			if (xl.getTrangThaiXL() == true) {
				countDa++;
			}
		}

		int countChua = 0;
		for (XuLy xl : xuLyIterable) {
			if (xl.getTrangThaiXL() == false) {
				countChua++;
			}
		}

		long tongTien = 0;
		for (XuLy xl : xuLyIterable) {
			if (xl.getSoTien() != null) {
				tongTien += xl.getSoTien();
			}
		}

		m.addAttribute("tongTien", tongTien);
		m.addAttribute("xuly", xuLyIterable);
		m.addAttribute("countDa", countDa);
		m.addAttribute("countChua", countChua);

		return "statistic";
	}

	@GetMapping("/filterBorrowing")
	public String filterBorrowing(@RequestParam("startDateBorrowing") String startDate,
			@RequestParam("endDateBorrowing") String endDate, Model m, HttpServletRequest request) throws ParseException {
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

		Iterable<ThanhVien> usersIterable = thanhVienService.getAll();
		Iterable<ThongTinSD> ttsdIterable = thongTinSDService.getAll();
		Iterable<XuLy> xuLyIterable = xuLyService.getAll();

		// Thành viên
		ArrayList<ThongTinSD> ttsdArr = new ArrayList<>();
		for (ThongTinSD ttsd : ttsdIterable) {
			if (ttsd.getTgVao() != null) {
				ttsdArr.add(ttsd);
			}
		}

		HashSet<Integer> thanhVienSet = new HashSet<>();
		for (ThongTinSD ttsd : ttsdArr) {
			thanhVienSet.add(ttsd.getThanhVienTTSD().getMaTV());
		}
		int soLuong = thanhVienSet.size();

		Set<String> uniqueKhoa = new HashSet<>();
		for (ThanhVien user : usersIterable) {
			uniqueKhoa.add(user.getKhoa());
		}

		Set<String> uniqueNganh = new HashSet<>();
		for (ThanhVien user : usersIterable) {
			uniqueNganh.add(user.getNganh());
		}

		int totalCheckIn = 0;
		for (ThanhVien tv : usersIterable) {
			if (tv.getCheckIn() == null) {
				tv.setCheckIn(0);
			}

			totalCheckIn += tv.getCheckIn();
		}

		m.addAttribute("totalCheckIn", totalCheckIn);
		m.addAttribute("soLuong", soLuong);
		m.addAttribute("users", usersIterable);
		m.addAttribute("nganh", uniqueNganh);
		m.addAttribute("khoa", uniqueKhoa);
		m.addAttribute("ttsd", ttsdArr);

		// Mượn thiết bị
		ArrayList<ThongTinSD> ttsdArrBorrow = new ArrayList<>();
		for (ThongTinSD ttsd : ttsdIterable) {
			if (ttsd.getTgMuon() != null && ttsd.getTgTra() != null) {
				ttsdArrBorrow.add(ttsd);
			}
		}

		m.addAttribute("ttsdBorrow", ttsdArrBorrow);
		m.addAttribute("luotMuon", ttsdArrBorrow.size());

		// Đang mượn thiết bị
		ArrayList<ThongTinSD> ttsdArrBorrowing = new ArrayList<>();
		for (ThongTinSD ttsd : ttsdIterable) {
			if (ttsd.getTgMuon() != null && ttsd.getTgTra() == null) {
				ttsdArrBorrowing.add(ttsd);
			}
		}

		ArrayList<ThongTinSD> ttsdArrBorrowingFilter = new ArrayList<>(ttsdArrBorrowing);
		for (ThongTinSD ttsd : ttsdArrBorrowing) {
			if (endDate != "") {
				Date endDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endDate);
				if (endDateTime.compareTo(ttsd.getTgMuon()) < 0) {
					ttsdArrBorrowingFilter.remove(ttsd);
				}
			}
		}

		m.addAttribute("ttsdBorrowing", ttsdArrBorrowingFilter);
		m.addAttribute("luotDangMuon", ttsdArrBorrowingFilter.size());

		// Vi phạm
		int countDa = 0;
		for (XuLy xl : xuLyIterable) {
			if (xl.getTrangThaiXL() == true) {
				countDa++;
			}
		}

		int countChua = 0;
		for (XuLy xl : xuLyIterable) {
			if (xl.getTrangThaiXL() == false) {
				countChua++;
			}
		}

		long tongTien = 0;
		for (XuLy xl : xuLyIterable) {
			if (xl.getSoTien() != null) {
				tongTien += xl.getSoTien();
			}
		}

		m.addAttribute("tongTien", tongTien);
		m.addAttribute("xuly", xuLyIterable);
		m.addAttribute("countDa", countDa);
		m.addAttribute("countChua", countChua);

		return "statistic";
	}

	@GetMapping("/filterViolate")
	public String filterViolate(@RequestParam("status") String status, Model m, HttpServletRequest request) {
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

		Iterable<ThanhVien> usersIterable = thanhVienService.getAll();
		Iterable<ThongTinSD> ttsdIterable = thongTinSDService.getAll();
		Iterable<XuLy> xuLyIterable = xuLyService.getAll();

		// Thành viên
		ArrayList<ThongTinSD> ttsdArr = new ArrayList<>();
		for (ThongTinSD ttsd : ttsdIterable) {
			if (ttsd.getTgVao() != null) {
				ttsdArr.add(ttsd);
			}
		}

		HashSet<Integer> thanhVienSet = new HashSet<>();
		for (ThongTinSD ttsd : ttsdArr) {
			thanhVienSet.add(ttsd.getThanhVienTTSD().getMaTV());
		}
		int soLuong = thanhVienSet.size();

		Set<String> uniqueKhoa = new HashSet<>();
		for (ThanhVien user : usersIterable) {
			uniqueKhoa.add(user.getKhoa());
		}

		Set<String> uniqueNganh = new HashSet<>();
		for (ThanhVien user : usersIterable) {
			uniqueNganh.add(user.getNganh());
		}

		int totalCheckIn = 0;
		for (ThanhVien tv : usersIterable) {
			if (tv.getCheckIn() == null) {
				tv.setCheckIn(0);
			}

			totalCheckIn += tv.getCheckIn();
		}

		m.addAttribute("totalCheckIn", totalCheckIn);
		m.addAttribute("users", usersIterable);
		m.addAttribute("soLuong", soLuong);
		m.addAttribute("nganh", uniqueNganh);
		m.addAttribute("khoa", uniqueKhoa);
		m.addAttribute("ttsd", ttsdArr);

		// Mượn thiết bị
		ArrayList<ThongTinSD> ttsdArrBorrow = new ArrayList<>();
		for (ThongTinSD ttsd : ttsdIterable) {
			if (ttsd.getTgMuon() != null && ttsd.getTgTra() != null) {
				ttsdArrBorrow.add(ttsd);
			}
		}

		m.addAttribute("ttsdBorrow", ttsdArrBorrow);
		m.addAttribute("luotMuon", ttsdArrBorrow.size());

		// Đang mượn thiết bị
		ArrayList<ThongTinSD> ttsdArrBorrowing = new ArrayList<>();
		for (ThongTinSD ttsd : ttsdIterable) {
			if (ttsd.getTgMuon() != null && ttsd.getTgTra() == null) {
				ttsdArrBorrowing.add(ttsd);
			}
		}

		m.addAttribute("ttsdBorrowing", ttsdArrBorrowing);
		m.addAttribute("luotDangMuon", ttsdArrBorrowing.size());

		// Vi phạm
		ArrayList<XuLy> xuLyArr = new ArrayList<>();
		if (status.equals("1")) {
			for (XuLy xl : xuLyIterable) {
				if (xl.getTrangThaiXL() == true) {
					xuLyArr.add(xl);
				}
			}
		} else if (status.equals("0")) {
			for (XuLy xl : xuLyIterable) {
				if (xl.getTrangThaiXL() == false) {
					xuLyArr.add(xl);
				}
			}
		} else {
			for (XuLy xl : xuLyIterable) {
				xuLyArr.add(xl);
			}
		}

		int countDa = 0;
		for (XuLy xl : xuLyArr) {
			if (xl.getTrangThaiXL() == true) {
				countDa++;
			}
		}

		int countChua = 0;
		for (XuLy xl : xuLyArr) {
			if (xl.getTrangThaiXL() == false) {
				countChua++;
			}
		}

		long tongTien = 0;
		for (XuLy xl : xuLyArr) {
			if (xl.getSoTien() != null) {
				tongTien += xl.getSoTien();
			}
		}

		m.addAttribute("tongTien", tongTien);
		m.addAttribute("xuly", xuLyArr);
		m.addAttribute("countDa", countDa);
		m.addAttribute("countChua", countChua);

		return "statistic";
	}

}

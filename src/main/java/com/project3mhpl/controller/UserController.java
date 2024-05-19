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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project3mhpl.dto.BaseResponse;
import com.project3mhpl.dto.CheckInResponseDto;
import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.service.ThanhVienService;
import com.project3mhpl.service.ThietBiService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	@Autowired
	private ThanhVienService thanhvienService;

	@Autowired
	private ThietBiService thietBiService;

	@GetMapping("/manage-users")
	public String getProfile(Model m, HttpServletRequest request) {
		Boolean isAuthenticated = thanhvienService.checkAuth(request);

		m.addAttribute("isAuthenticated", isAuthenticated);
		if (isAuthenticated == false) {
			return "redirect:sign-in";
		}

		m.addAttribute("isAdmin", thanhvienService.checkAdmin(request));
		m.addAttribute("users", thanhvienService.getAll());
		m.addAttribute("newUser", new ThanhVien());
		m.addAttribute("devices", thietBiService.getAll());

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

	@PostMapping("/user/update/{maTV}")
	public String updateUser(@PathVariable Integer maTV, ThanhVien tv) {

		thanhvienService.updateTV(maTV, tv);

		return "redirect:/dashboard#manage-users";
	}

	@PostMapping("/user/add")
	public String addUser(ThanhVien tv, Model m) {

		tv.setMaTV((int) tv.getMaTV());

		tv.setIsAdmin(false);
		if (tv.getMaTV().toString().length() != 10 || !tv.getMaTV().toString().matches("\\d+")) {
			m.addAttribute("errorMessage", "Mã thành viên đủ 10 số và không chứa kí tự!");
			m.addAttribute("thanhvien", tv);
			return "/user/add";
		}
		if (!tv.getHoten().matches("^[\\p{L}\\s]+$")) {
			m.addAttribute("errorMessage", "Tên thành viên chỉ chứa chữ cái!");
			m.addAttribute("thanhvien", tv);
			return "/user/add";
		}
		if (!tv.getKhoa().matches("^[\\p{L}\\s]+$") || !tv.getNganh().matches("^[\\p{L}\\s]+$")) {
			m.addAttribute("errorMessage", "Khoa và ngành chỉ chứa chữ cái!");
			m.addAttribute("thanhvien", tv);
			return "/user/add";
		}
		if (!tv.getSdt().matches("0\\d{9}")) {
			m.addAttribute("errorMessage", "Số điện thoại không đúng dịnh dạng!");
			m.addAttribute("thanhvien", tv);
			return "/user/add";
		}
		if (!tv.getEmail().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
			m.addAttribute("errorMessage", "Email không đúng dịnh dạng!");
			m.addAttribute("thanhvien", tv);
			return "/user/add";
		}
		tv.setMaTV(Integer.parseInt(tv.getMaTV().toString()));
		thanhvienService.saveTV(tv);

		return "redirect:/dashboard#manage-users";
	}

	@PostMapping("/user/check-in/{maTV}")
	@ResponseBody
	public BaseResponse<CheckInResponseDto> checkInUser(@PathVariable Integer maTV) {

		System.out.println(maTV);

		return thanhvienService.checkIn(maTV);
	}

}

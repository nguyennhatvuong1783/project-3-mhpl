package com.project3mhpl.controller;

import com.project3mhpl.dto.XuLyDuyetDTO;
import com.project3mhpl.entity.XuLy;
import com.project3mhpl.service.ThanhVienService;
import com.project3mhpl.service.XulyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

/**
 *
 * @author Nguyen Minh Tri
 */
@Controller
public class XulyController {
	@Autowired
	private XulyService xulyService;

	@Autowired
	private ThanhVienService thanhVienService;

	@GetMapping("/xu-ly")
	public String getAll(Model m, HttpServletRequest request) {
		Boolean isAuthenticated = thanhVienService.checkAuth(request);

		m.addAttribute("isAuthenticated", isAuthenticated);
		if (isAuthenticated == false) {
			return "redirect:sign-in";
		}
		m.addAttribute("isAdmin", thanhVienService.checkAdmin(request));

		Iterable<XuLy> list = xulyService.getAll();
		ArrayList<XuLy> xuLyArrayList = new ArrayList<>();
		for (XuLy t : list) {
			xuLyArrayList.add(t);
		}

		m.addAttribute("data", xuLyArrayList);
		return "xu-ly";
	}

	@PostMapping("/xu-ly/duyet")
	public String submitData(
			Model m,
			XuLyDuyetDTO form,
			HttpServletRequest request) {
		Boolean isAuthenticated = thanhVienService.checkAuth(request);

		m.addAttribute("isAuthenticated", isAuthenticated);
		if (isAuthenticated == false) {
			return "redirect:sign-in";
		}

		XuLy member = xulyService.getOne(form.getMaXL());

		if (member == null) {
			m.addAttribute("errorMessage", "Vi phạm cần xử lý không hợp lệ");
			return "xu-ly";
		}

		Iterable<XuLy> list = xulyService.getAll();
		ArrayList<XuLy> xuLyArrayList = new ArrayList<>();
		for (XuLy t : list) {
			xuLyArrayList.add(t);
		}

		m.addAttribute("data", xuLyArrayList);

		// Xử lý dữ liệu và trả về view hoặc thực hiện các hành động khác
		// ...
		return "xu-ly";
	}
}

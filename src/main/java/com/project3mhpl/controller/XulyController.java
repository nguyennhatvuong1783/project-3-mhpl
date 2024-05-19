package com.project3mhpl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.project3mhpl.dto.CreateViolatedDto;
import com.project3mhpl.dto.UpdateViolatedDto;
import com.project3mhpl.entity.XuLy;
import com.project3mhpl.service.ThanhVienService;
import com.project3mhpl.service.XulyService;

import jakarta.servlet.http.HttpServletRequest;

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

		m.addAttribute("data", xulyService.getAll());
		m.addAttribute("users", thanhVienService.getAll());
		m.addAttribute("violated", new CreateViolatedDto());
		return "xu-ly";
	}

	@PostMapping("/xu-ly")
	public String createViolation(CreateViolatedDto form) {
		xulyService.createViolation(form);

		return "redirect:/dashboard#handle-violations";
	}

	@PostMapping("/xu-ly/duyet/{maXL}")
	public String resolveViolated(@PathVariable Integer maXL) {
		xulyService.resolveViolated(maXL);

		return "redirect:/dashboard#handle-violations";
	}

	@PostMapping("/xu-ly/delete")
	public String deleteViolation(XuLy xl) {

		xulyService.deleteXL(xl.getMaXL());

		return "redirect:/dashboard#handle-violations";
	}

	@PostMapping("/xu-ly/update/{maXL}")
	public String updateUser(@PathVariable Integer maXL, UpdateViolatedDto form) {

		xulyService.updateVP(maXL, form);

		return "redirect:/dashboard#handle-violations";
	}

}

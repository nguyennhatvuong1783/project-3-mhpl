package com.project3mhpl.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.project3mhpl.dto.CreateViolatedDto;
import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.entity.XuLy;
import com.project3mhpl.repository.XuLyRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Nguyen Minh Tri
 */
@Service
public class XulyService {
	@Autowired
	private XuLyRepository xuLyRepository;

	@Autowired
	private ThanhVienService thanhVienService;

	public Iterable<XuLy> getAll() {
		return xuLyRepository.findAll();
	}

	public XuLy getOne(Integer maXL) {
		try {
			return xuLyRepository.findByMaXL(maXL).get(0);
		} catch (Exception e) {
			System.out.println("Error findByXuLy_MaTB " + e.getMessage());
			return null;
		}
	}

	public Boolean saveXl(XuLy xl) {
		return xuLyRepository.save(xl) != null;
	}

	@SuppressWarnings("null")
	public List<XuLy> getTTXLByIdTV(HttpServletRequest request) {
		Cookie c = WebUtils.getCookie(request, "auth");
		List<XuLy> authUser = xuLyRepository.findByThanhVienXL_MaTV(Integer.parseInt(c.getValue()));
		return authUser;
	}

	public void createViolation(CreateViolatedDto form) {
		ThanhVien tv = thanhVienService.getById(form.getMaTV()).get();

		if (tv == null) {
			return;
		}

		XuLy xl = new XuLy();
		xl.setThanhVienXL(tv);
		xl.setHinhThucXL(form.hinhThucXL);
		xl.setSoTien(form.soTien);
		xl.setTrangThaiXL(false);

		saveXl(xl);
	}

	public void resolveViolated(Integer maXL) {
		XuLy xl = getOne(maXL);

		xl.setTrangThaiXL(true);
		xl.setNgayXL(new Date());

		saveXl(xl);
	}
}

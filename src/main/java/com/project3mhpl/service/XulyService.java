package com.project3mhpl.service;

import com.project3mhpl.entity.XuLy;
import com.project3mhpl.repository.XuLyRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author Nguyen Minh Tri
 */
@Service
public class XuLyService {
	@Autowired
	private XuLyRepository xuLyRepository;

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
}

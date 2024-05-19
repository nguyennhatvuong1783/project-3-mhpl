/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.project3mhpl.dto.BaseResponse;
import com.project3mhpl.dto.CheckInResponseDto;
import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.entity.XuLy;
import com.project3mhpl.repository.ThanhVienRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author ADMIN
 */
@Service
public class ThanhVienService {
	@Autowired
	private ThanhVienRepository thanhvienRepository;

	@Autowired
	private JavaMailSender mailSender;

	private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String generateRandomString(int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(CHARACTERS.length());
			char randomChar = CHARACTERS.charAt(randomIndex);
			sb.append(randomChar);
		}
		return sb.toString();
	}

	public Iterable<ThanhVien> getAll() {
		return thanhvienRepository.findAll();
	}

	public Optional<ThanhVien> getById(Integer id) {
		return thanhvienRepository.findById(id);
	}

	public Boolean verifyUser(Integer maTV, String password) {
		Optional<ThanhVien> thanhVien = thanhvienRepository.findById(maTV);

		return thanhVien.isPresent() && thanhVien.get().getPassword().equals(password);
	}

	public Boolean isAdminUser(Integer maTV) {
		Optional<ThanhVien> thanhVien = thanhvienRepository.findById(maTV);

		return thanhVien.isPresent() && thanhVien.get().getIsAdmin() == true;
	}

	public Boolean saveTV(ThanhVien tv) {
		return thanhvienRepository.save(tv) != null;
	}

	public Boolean checkAuth(HttpServletRequest request) {
		Cookie c = WebUtils.getCookie(request, "auth");

		return c != null && c.getValue() != null && c.getValue() != "";
	}

	public Boolean checkAdmin(HttpServletRequest request) {
		Cookie c = WebUtils.getCookie(request, "auth");

		Boolean isAmin = false;

		try {
			ThanhVien tv = getProfile(request);
			isAmin = tv.getIsAdmin();
		} catch (Exception e) {
			return false;
		}

		return c != null && c.getValue() != null && c.getValue() != "" && isAmin == true;
	}

	public Cookie createAuthSession(String maTV) {
		Cookie cookie = new Cookie("auth", maTV);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);

		return cookie;
	}

	public Cookie clearAuthSession() {
		Cookie cookie = new Cookie("auth", "");
		cookie.setSecure(true);
		cookie.setHttpOnly(true);

		return cookie;
	}

	@SuppressWarnings("null")
	public ThanhVien getProfile(HttpServletRequest request) {
		Cookie c = WebUtils.getCookie(request, "auth");

		Optional<ThanhVien> authUser = thanhvienRepository.findById(Integer.parseInt(c.getValue()));

		return authUser.get();

	}

	public void saveListTV(MultipartFile file) throws IOException {
		List<ThanhVien> thanhVienList = new ArrayList<ThanhVien>();

		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

		XSSFSheet worksheet = workbook.getSheetAt(0);

		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

			XSSFRow row = worksheet.getRow(i);

			ThanhVien tv = new ThanhVien();

			tv.setMaTV(Integer.parseInt(row.getCell(0).getStringCellValue()));
			tv.setHoten(row.getCell(1).getStringCellValue());
			tv.setKhoa(row.getCell(2).getStringCellValue());
			tv.setNganh(row.getCell(3).getStringCellValue());
			tv.setSdt(row.getCell(4).getStringCellValue());
			tv.setPassword(row.getCell(5).getStringCellValue());
			tv.setEmail(row.getCell(6).getStringCellValue());
			tv.setIsAdmin(false);

			thanhVienList.add(tv);

		}

		workbook.close();

		thanhvienRepository.saveAll(thanhVienList);
	}

	public Boolean deleteTV(Integer id) {
		if (id == null) {
			return false;
		}

		thanhvienRepository.deleteById(id);

		return true;
	}

	public void updateTV(Integer id, ThanhVien tv) {
		ThanhVien thanhVien = thanhvienRepository.findById(id).get();

		if (tv.getEmail() != null) {
			thanhVien.setEmail(tv.getEmail());
		}

		if (tv.getHoten() != null) {
			thanhVien.setHoten(tv.getHoten());
		}

		if (tv.getKhoa() != null) {
			thanhVien.setKhoa(tv.getKhoa());
		}

		if (tv.getNganh() != null) {
			thanhVien.setNganh(tv.getNganh());
		}

		if (tv.getSdt() != null) {
			thanhVien.setSdt(tv.getSdt());
		}

		thanhvienRepository.save(thanhVien);
	}

	public ThanhVien findBySdt(String sdt) {
		return thanhvienRepository.findBySdt(sdt);
	}

	public ThanhVien findByEmail(String email) {
		return thanhvienRepository.findByEmail(email);
	}

	public ThanhVien findByMaTV(Integer maTV) {
		return thanhvienRepository.findByMaTV(maTV);
	}

	public Boolean resetPassword(String email) {
		ThanhVien tv = thanhvienRepository.findByEmail(email);

		if (tv == null) {
			return false;
		}

		String password = generateRandomString(6);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Reset password");
		message.setText("Your password is: " + password);

		mailSender.send(message);

		tv.setPassword(password);

		thanhvienRepository.save(tv);

		return true;
	}

	public BaseResponse<CheckInResponseDto> checkIn(Integer maTV) {

		BaseResponse<CheckInResponseDto> result = new BaseResponse<CheckInResponseDto>();

		ThanhVien tv = thanhvienRepository.findByMaTV(maTV);

		if (tv == null) {
			result.setIsSuccess(false);
			result.setMessage("Thành viên không tồn tại");
			return result;
		}

		if (!tv.getXuLy().isEmpty()) {
			for (XuLy xl : tv.getXuLy()) {
				if (!xl.getTrangThaiXL()) {
					result.setIsSuccess(false);
					result.setMessage("Thành viên có vi phạm đang xử lý");

					return result;
				}
			}
		}

		// Clear sensitive data before return
		tv.setPassword(null);
		tv.setXuLy(null);

		result.setIsSuccess(true);
		result.setMessage("Check in thành công");

		result.setData(new CheckInResponseDto(tv, new Date()));

		return result;
	}

}

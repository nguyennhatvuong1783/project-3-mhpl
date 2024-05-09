/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.entity.ThongTinSD;
import com.project3mhpl.service.ThanhVienService;
import com.project3mhpl.service.ThongTinSDService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nguyen Nhat Vuong
 */
@Controller
public class ThanhVienController {
    @Autowired
    private ThanhVienService thanhvienService;
 
   private static final Logger logger = LoggerFactory.getLogger(ThanhVienController.class);
    @GetMapping("/profile")
public String getProfile(HttpSession session, Model m) {
    ThanhVien tv = (ThanhVien) session.getAttribute("tv");
    if (tv!=null) {
        m.addAttribute("data", tv);
        return "profile";
    } else {
        // m.addAttribute("errorMessage", "Đăng nhập để xem thông tin tài khoản");
        return "ìndex";
    }
}
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.entity.ThongTinSD;
import com.project3mhpl.service.ThanhVienService;
import com.project3mhpl.service.ThongTinSDService;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Nguyen Nhat Vuong
 */
@Controller
public class ThanhVienController {
    @Autowired
    private ThanhVienService thanhvienService;
 
   
    @GetMapping("/profile")
    public String getAll(Model m)
    {   
        Optional<ThanhVien> tv= thanhvienService.getById(1121530087);
        ThanhVien thanhvien =tv.get();
        m.addAttribute("data", thanhvien);
        return "profile";
    }
}

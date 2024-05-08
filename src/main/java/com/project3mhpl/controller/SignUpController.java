/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.repository.ThanhVienRepository;
import com.project3mhpl.service.ThanhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author nhan1
 */
@Controller
public class SignUpController {
    @Autowired
    private ThanhVienRepository thanhVienRepository;
    
    @GetMapping("/sign_up")
    public String showLogin(Model m){
        ThanhVien ThanhVien = new ThanhVien();
        ThanhVien.setMaTV(9);
             
        m.addAttribute("ThanhVien",ThanhVien);
        return "sign_up";
    }
    
    @PostMapping("/process_register")
    public String processRegistration(@ModelAttribute("ThanhVien") ThanhVien tv){
        
        thanhVienRepository.save(tv);
        
        
        return "login";
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.entity.ThietBi;
import com.project3mhpl.service.ThanhVienService;
import com.project3mhpl.service.ThietBiService;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author nhan1
 */
@Controller
public class loginController {
    
    @Autowired
    private ThanhVienService thanhVienService;
    
    
    @GetMapping("/login")
    public String showLogin(Model m){
        return "login";
    }
    
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String login(@ModelAttribute @RequestParam("username") String username, @RequestParam("password") String password, Model v){
        
        Iterable<ThanhVien> list = thanhVienService.getAll();

        int check=0;
        for(ThanhVien a : list){
            if(a.getmaTV()==Integer.parseInt(username)){
                if(a.getPassword().equals(password)){
                    return "redirect:/all";
                }
                else{
               
                  v.addAttribute("errorMessage", "Sai mật khẩu"); // Thêm thông báo lỗi vào Model
                return "login";
                }
            }
        }
        
        v.addAttribute("errorMessage", "Tài khoản không tồn tại");
                return "login";
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import com.project3mhpl.service.ThanhVienService;
import com.project3mhpl.service.ThongTinSDService;

import jakarta.servlet.http.HttpServletRequest;

import com.project3mhpl.entity.ThongTinSD;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tran Nhat Qui
 */
@RestController
public class DataController {
    @Autowired
    private ThongTinSDService thongTinSDService;
    private ThanhVienService thanhVienService;

    @GetMapping("/checkData")
    public String checkData(@RequestParam("index") int index, HttpServletRequest request, Model m) {
        Boolean isAuthenticated = thanhVienService.checkAuth(request);

        m.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated == false) {
            return "redirect:sign-in";
        }

        // Xử lý
        List<ThongTinSD> list = thongTinSDService.getTTSDByIdTB(index);
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTgDatcho() != null) {
                calendar.setTime(list.get(i).getTgDatcho());
                calendar.add(Calendar.HOUR_OF_DAY, 1);
                Date tgDatChoPlus1h = calendar.getTime();
                if (currentDate.compareTo(tgDatChoPlus1h) <= 0) {
                    return "alert";
                }
            } else {
                if (currentDate.compareTo(list.get(i).getTgTra()) <= 0) {
                    return "alert";
                }
            }
        }
        
        return "modal";
    }
}

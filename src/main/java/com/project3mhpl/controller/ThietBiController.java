/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import com.project3mhpl.entity.ThietBi;
import com.project3mhpl.repository.ThietBiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Nguyen Nhat Vuong
 */
@Controller
public class ThietBiController {
    @Autowired
    private ThietBiRepository thietBiRepository;
   
    @GetMapping("/all")
    public String getAll(Model m)
    {
        Iterable<ThietBi> list = thietBiRepository.findAll();
        m.addAttribute("data", list);
        return "thiet_bi_all";
    }
}

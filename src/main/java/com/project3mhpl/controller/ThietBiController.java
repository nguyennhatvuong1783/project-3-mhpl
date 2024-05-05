/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import com.project3mhpl.entity.FormData;
import com.project3mhpl.entity.ThietBi;
import com.project3mhpl.entity.ThongTinSD;
import com.project3mhpl.repository.ThietBiRepository;
import com.project3mhpl.service.ThietBiService;
import com.project3mhpl.service.ThongTinSDService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Nguyen Nhat Vuong
 */
@Controller
public class ThietBiController {
    @Autowired
    private ThietBiService thietBiService;
 
   
    @GetMapping("/dat-muon-thiet-bi")
    public String getAll(Model m)
    {
        Iterable<ThietBi> list = thietBiService.getAll();
        ArrayList<ThietBi> products = new ArrayList<>();
        for(ThietBi t : list){
            products.add(t);
        }

        m.addAttribute("formData",new FormData());
        m.addAttribute("data", products);
        return "thiet_bi_all";
    }
    
    @PostMapping("/submit-data")
   public String submitData(@ModelAttribute("formData") FormData formData) {
    // Xử lý dữ liệu và trả về view hoặc thực hiện các hành động khác
    String submittedData = formData.getData();
    // ...
    return "result-page";
  }

    
   
    
    
    
}
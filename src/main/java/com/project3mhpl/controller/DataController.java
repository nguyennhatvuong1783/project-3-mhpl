/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tran Nhat Qui
 */
@RestController
public class DataController {
    @GetMapping("/checkData")
    public String checkData(@RequestParam("index") int index) {
    // Xử lý ID và trả về kết quả tương ứng
    // ví dụ checkThongTinSD(inđex)==false
    if (index == 1) {
      return "modal";
    } else {
      return "alert";
    }
   }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.service;

import com.project3mhpl.entity.ThongTinSD;
import com.project3mhpl.repository.ThongTinSDRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Tran Nhat Qui
 */
public class ThongTinSDService {
    @Autowired
    ThongTinSDRepository thongtinsudungrepository;
    
    public Iterable<ThongTinSD> getAll(){
        return thongtinsudungrepository.findAll();
    }
}

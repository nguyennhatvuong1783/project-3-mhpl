/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.service;

import com.project3mhpl.entity.ThongTinSD;
import com.project3mhpl.repository.ThongTinSDRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tran Nhat Qui
 */
@Service
public class ThongTinSDService {
    @Autowired
    private ThongTinSDRepository thongTinSDRepository;
    
    public Iterable<ThongTinSD> getAll(){
        return thongTinSDRepository.findAll();
    }
    
    public List<ThongTinSD> getTTSDByIdTB(Integer idTB) {
        return thongTinSDRepository.findByThietBi_MaTB(idTB);
    }
}

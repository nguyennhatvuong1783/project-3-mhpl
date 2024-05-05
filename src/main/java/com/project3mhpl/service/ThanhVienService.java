/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.service;
import com.project3mhpl.entity.ThanhVien;
import com.project3mhpl.repository.ThanhVienRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author ADMIN
 */
@Service
public class ThanhVienService {
    @Autowired
    private ThanhVienRepository thanhvienRepository;
    public Iterable<ThanhVien> getAll(){
        return thanhvienRepository.findAll();
    }
    public Optional<ThanhVien> getById(int id) {
        return thanhvienRepository.findById(id);
    }

}

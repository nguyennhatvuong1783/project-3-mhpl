/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.service;

import com.project3mhpl.entity.ThietBi;
import com.project3mhpl.repository.ThietBiRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tran Nhat Qui
 */
@Service
public class ThietBiService {
    @Autowired
    private ThietBiRepository thietbiRepository;
    
    public Iterable<ThietBi> getAll(){
        return thietbiRepository.findAll();
    }
    
    public Optional<ThietBi> getByID(Integer maTB){
        return thietbiRepository.findById(maTB);
    }
    
    public boolean store(ThietBi thietBi) {
        try {
            thietbiRepository.save(thietBi);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public boolean destroy(Integer maTB) {
        try {
            thietbiRepository.deleteById(maTB);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}

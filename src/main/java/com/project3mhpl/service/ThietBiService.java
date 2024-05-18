/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.service;

import com.project3mhpl.entity.ThietBi;
import com.project3mhpl.repository.ThietBiRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    
    public void importListTB(MultipartFile file) throws IOException{
        List<ThietBi> thietbiList = new ArrayList<ThietBi>();
        
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

	XSSFSheet worksheet = workbook.getSheetAt(0);
        
        for(int i=1; i< worksheet.getPhysicalNumberOfRows(); i++){
            XSSFRow row = worksheet.getRow(i);
            
            ThietBi tb = new ThietBi();
            
            tb.setMaTB(Integer.parseInt(row.getCell(0).getStringCellValue()));
            tb.setTenTB(row.getCell(1).getStringCellValue());
            tb.setMoTaTB(row.getCell(2).getStringCellValue());
            
            thietbiList.add(tb);
        }
        
        workbook.close();
        
        thietbiRepository.saveAll(thietbiList);
    }
}

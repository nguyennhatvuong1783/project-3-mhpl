package com.project3mhpl.service;

import com.project3mhpl.entity.XuLy;
import com.project3mhpl.repository.XuLyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nguyen Minh Tri
 */
@Service
public class XulyService {
    @Autowired
    private XuLyRepository xuLyRepository;

    public Iterable<XuLy> getAll(){
        return xuLyRepository.findAll();
    }

    public XuLy getOne(Integer maXL) {
        try {
            return xuLyRepository.findByMaXL(maXL).get(0);
        } catch (Exception e) {
            System.out.println("Error findByXuLy_MaTB " + e.getMessage());
            return null;
        }
    }

    public Boolean saveXl(XuLy xl) {
        return xuLyRepository.save(xl) != null;
    }
}

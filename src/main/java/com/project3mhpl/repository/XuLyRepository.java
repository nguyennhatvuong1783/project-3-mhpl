/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project3mhpl.repository;

import com.project3mhpl.entity.XuLy;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nguyen Nhat Vuong
 */
@Repository
public interface XuLyRepository extends CrudRepository<XuLy, Integer> {
List<XuLy> findByThanhVienXL_MaTV(Integer maTV);
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project3mhpl.repository;

import com.project3mhpl.entity.ThanhVien;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nguyen Nhat Vuong
 */
@Repository
public interface ThanhVienRepository extends CrudRepository<ThanhVien, Integer> {
	ThanhVien findBySdt(String sdt);

	ThanhVien findByEmail(String email);

        ThanhVien findByMaTV(Integer maTV);
}

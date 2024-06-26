/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project3mhpl.repository;

import com.project3mhpl.entity.ThietBi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nguyen Nhat Vuong
 */
@Repository
public interface ThietBiRepository extends CrudRepository<ThietBi, Integer> {}

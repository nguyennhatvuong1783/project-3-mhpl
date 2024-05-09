/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

/**
 *
 * @author Nguyen Nhat Vuong
 */
@Data
@Entity
@Table(name = "thietbi")
public class ThietBi {
    @Id
    @Column(name = "matb")
    private Integer maTB;
    
    @Column(name = "tentb")
    private String tenTB;
    
    @Column(name = "motatb")
    private String moTaTB;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "thietBi")
    private List<ThongTinSD> thongTinSD;

    public ThietBi() {
    }

    public ThietBi(Integer maTB, String tenTB, String moTaTB) {
        this.maTB = maTB;
        this.tenTB = tenTB;
        this.moTaTB = moTaTB;
    }

}

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
    private Integer maTB;
    
    @Column
    private String tenTB;
    
    @Column
    private String mo_tatb;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "thietBi")
    private List<ThongTinSD> thongTinSD;

    public ThietBi() {
    }

    public ThietBi(Integer MaTB, String TenTB, String MoTaTB) {
        this.maTB = MaTB;
        this.tenTB = TenTB;
        this.mo_tatb = MoTaTB;
    }

}

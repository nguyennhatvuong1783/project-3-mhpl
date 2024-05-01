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
    private Integer MaTB;
    
    @Column
    private String TenTB;
    
    @Column
    private String mo_tatb;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "thietBi")
    private List<ThongTinSD> thongTinSD;

    public ThietBi() {
    }

    public ThietBi(Integer MaTB, String TenTB, String MoTaTB) {
        this.MaTB = MaTB;
        this.TenTB = TenTB;
        this.mo_tatb = MoTaTB;
    }

    
    
    public Integer getMaTB(){
        return MaTB;
    }
    
    public void setMaTB(Integer MaTB){
        this.MaTB = MaTB;
    }
    
    public String getTenTB(){
        return TenTB;
    }
    
    public void setTenTB(String TenTB){
        this.TenTB = TenTB;
    }
    
    public String getMotaTB(){
        return mo_tatb;
    }
    
    public void setMotaTB(String MoTaTB){
        this.mo_tatb = MoTaTB;
    }
    
    
}

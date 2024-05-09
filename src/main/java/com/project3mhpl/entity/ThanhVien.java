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
@Table(name = "thanhvien")
public class ThanhVien {
    @Id
    @Column(name = "matv")
    private Integer maTV;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "hoten")
    private String hoten;
    
    @Column(name = "khoa")
    private String khoa;
    
    @Column(name = "nganh")
    private String nganh;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "sdt")
    private String sdt; // Thay đổi kiểu dữ liệu thành String
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "thanhVienTTSD")
    private List<ThongTinSD> thongTinSD;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "thanhVienXL")
    private List<XuLy> xuLy;

    public ThanhVien() {
    }

    public ThanhVien(Integer maTV, String password, String hoten, String khoa, String nganh, String email, String sdt) { // Thay đổi kiểu dữ liệu của tham số sdt
        this.maTV = maTV;
        this.password = password;
        this.hoten = hoten;
        this.khoa = khoa;
        this.nganh = nganh;
        this.email = email;
        this.sdt = sdt;
    }
}

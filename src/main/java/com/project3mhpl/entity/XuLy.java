/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3mhpl.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author Nguyen Nhat Vuong
 */
@Data
@Entity
@Table(name = "xuly")
public class XuLy {
    @Id
    private Integer maXL;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MaTV", nullable = false)
    private ThanhVien thanhVienXL;
    
    @Column
    private String hinhThucXL;
    
    @Column
    private Long soTien;
    
    @Column
    private Date ngayXL;
    
    @Column
    private Boolean trangThaiXL;
    
    private Integer soluong;

    public XuLy() {
    }

    public XuLy(Integer maXL, ThanhVien thanhVienXL, String hinhThucXL, Long soTien, Date ngayXL, Boolean trangThaiXL, Integer soluong) {
        this.maXL = maXL;
        this.thanhVienXL = thanhVienXL;
        this.hinhThucXL = hinhThucXL;
        this.soTien = soTien;
        this.ngayXL = ngayXL;
        this.trangThaiXL = trangThaiXL;
        this.soluong = soluong;
    }
}

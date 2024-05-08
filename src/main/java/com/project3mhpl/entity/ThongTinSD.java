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
@Table(name = "thongtinsd")
public class ThongTinSD {
    @Id
    private Integer maTT;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maTV", nullable = false)
    private ThanhVien thanhVienTTSD;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maTB", nullable = false)
    private ThietBi thietBi;
    
    @Column(name = "TGDatcho")
    private Date tgDatcho;
    
    @Column
    private Date tgVao;
    
    @Column(name = "TGMuon")
    private Date tgMuon;
    
    @Column(name = "TGTra")
    private Date tgTra;
    
    //Bổ sung vài cái cho việc làm thống kê
    private Integer soluong;
    
    private String khoa;
    
    private String nganh;
    
    private String tenTB;

    public ThongTinSD() {
    }

    public ThongTinSD(Integer maTT, ThanhVien thanhVienTTSD, ThietBi thietBi, Date tgDatcho, Date tgVao, Date tgMuon, Date tgTra, Integer soluong, String khoa, String nganh, String tenTB) {
        this.maTT = maTT;
        this.thanhVienTTSD = thanhVienTTSD;
        this.thietBi = thietBi;
        this.tgDatcho = tgDatcho;
        this.tgVao = tgVao;
        this.tgMuon = tgMuon;
        this.tgTra = tgTra;
        this.soluong = soluong;
        this.khoa = khoa;
        this.nganh = nganh;
        this.tenTB = tenTB;
    }
}

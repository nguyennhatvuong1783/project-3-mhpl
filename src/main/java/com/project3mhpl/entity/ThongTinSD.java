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
    @Column(name = "matt")
    private Integer maTT;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matv", nullable = false)
    private ThanhVien thanhVienTTSD;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matb", nullable = false)
    private ThietBi thietBi;
    
    @Column(name = "tgdatcho")
    private Date tgDatcho;
    
    @Column(name = "tgvao")
    private Date tgVao;
    
    @Column(name = "tgmuon")
    private Date tgMuon;
    
    @Column(name = "tgtra")
    private Date tgTra;
    
    public ThongTinSD() {
    }

    public ThongTinSD(Integer maTT, ThanhVien thanhVienTTSD, ThietBi thietBi, Date tgDatcho, Date tgVao, Date tgMuon, Date tgTra) {
        this.maTT = maTT;
        this.thanhVienTTSD = thanhVienTTSD;
        this.thietBi = thietBi;
        this.tgDatcho = tgDatcho;
        this.tgVao = tgVao;
        this.tgMuon = tgMuon;
        this.tgTra = tgTra;
    }
}

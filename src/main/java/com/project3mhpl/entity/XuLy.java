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
	@Column(name = "maxl")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maXL;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "matv", nullable = false)
	private ThanhVien thanhVienXL;

	@Column(name = "hinhthucxl")
	private String hinhThucXL;

	@Column(name = "sotien", length = 100, columnDefinition = "INT")
	private Long soTien;

	@Column(name = "ngayxl", nullable = true, columnDefinition = "DATE")
	private Date ngayXL;

	@Column(name = "trangthaixl", length = 2, columnDefinition = "INT")
	private Boolean trangThaiXL;

	public XuLy() {
	}

	public XuLy(Integer maXL, ThanhVien thanhVienXL, String hinhThucXL, Long soTien, Date ngayXL, Boolean trangThaiXL) {
		this.maXL = maXL;
		this.thanhVienXL = thanhVienXL;
		this.hinhThucXL = hinhThucXL;
		this.soTien = soTien;
		this.ngayXL = ngayXL;
		this.trangThaiXL = trangThaiXL;
	}
}

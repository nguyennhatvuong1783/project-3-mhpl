package com.project3mhpl.dto;

public class UpdateViolatedDto {
	public Integer maTV;
	public String hinhThucXL;
	public Long soTien;

	public UpdateViolatedDto() {

	}

	public Integer getMaTV() {
		return maTV;
	}

	public void setMaTV(Integer maTV) {
		this.maTV = maTV;
	}

	public String getHinhThucXL() {
		return hinhThucXL;
	}

	public void setHinhThucXL(String hinhThucXL) {
		this.hinhThucXL = hinhThucXL;
	}

	public Long getSoTien() {
		return soTien;
	}

	public void setSoTien(Long soTien) {
		this.soTien = soTien;
	}

	public void log() {
		System.out.println("CreateViolatedDto{" + "maTV=" + maTV + ", hinhThucXL='" + hinhThucXL + '\'' + ", soTien="
				+ soTien + '}');
	}

	public UpdateViolatedDto(Integer maTV, String hinhThucXL, Long soTien) {
		this.maTV = maTV;
		this.hinhThucXL = hinhThucXL;
		this.soTien = soTien;
	}
}

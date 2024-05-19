package com.project3mhpl.dto;

import java.util.Date;

import com.project3mhpl.entity.ThanhVien;

public class CheckInResponseDto {
	public ThanhVien user;
	public Date checkInAt;

	public CheckInResponseDto(ThanhVien user, Date checkInAt) {
		this.user = user;
		this.checkInAt = checkInAt;
	}

	public CheckInResponseDto() {
	}

	public ThanhVien getUser() {
		return user;
	}

	public void setUser(ThanhVien user) {
		this.user = user;
	}

	public Date getCheckInAt() {
		return checkInAt;
	}

	public void setCheckInAt(Date checkInAt) {
		this.checkInAt = checkInAt;
	}

}

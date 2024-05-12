package com.project3mhpl.dto;

public class ChangePasswordDto {
	public String password;
	public String newPassword;
	public String confirmPassword;

	public ChangePasswordDto(String password, String newPassword, String confirmPassword) {
		this.password = password;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}

	public ChangePasswordDto() {

	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

package com.project3mhpl.dto;

public class BaseResponse<T> {
	public T data;
	public Boolean isSuccess;
	public String message;

	public BaseResponse(Boolean isSuccess, String message, T data) {
		this.data = data;
		this.isSuccess = isSuccess;
		this.message = message;
	}

	public BaseResponse() {
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

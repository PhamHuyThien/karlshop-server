package com.thiendz.j6.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseDTO<T> {
	private int status;
	private String message;
	private String token;
	private T data;
	public ResponseDTO(int status){
		this.status = status;
	}
	public ResponseDTO(int status, String message){
		this(status);
		this.message = message;
	}
}

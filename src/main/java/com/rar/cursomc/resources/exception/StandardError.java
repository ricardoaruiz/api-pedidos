package com.rar.cursomc.resources.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StandardError implements Serializable {

	private static final long serialVersionUID = -9068269626064007692L;
	
	private Integer status;
	private String msg;
	private Long timeStamp;
	
	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty(value = "errors")
	private List<FieldMessage> fields = new ArrayList<FieldMessage>();
	
	public StandardError(Integer status, String msg, Long timeStamp) {
		super();
		this.status = status;
		this.msg = msg;
		this.timeStamp = timeStamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public List<FieldMessage> getFields() {
		return fields;
	}
	
	public void setFields(List<FieldMessage> fields) {
		this.fields = fields;
	}

	public void addError(String fieldName, String message) {
		this.fields.add(new FieldMessage(fieldName, message));
	}	
	
}

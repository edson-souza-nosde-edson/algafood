package com.ejs.algaworksCurso.api.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Problem {
	
	private Integer status;
	private String type;
	private String title;
	private String detail;
	
	public static class Builder{
		private Integer status;
		private String type;
		private String title;
		private String detail;
		
		public Builder() {
		}
		
		public Builder status(Integer status) {
			this.status = status;
			return this;
		}
		public Builder type( String type) {
			this.type = type;
			return this;
		}
		public Builder title(String title) {
			this.title = title;
			return this;
		}
		public Builder detail(String detail) {
			this.detail = detail;
			return this;
		}
		
		public Problem build() {
			Problem p = new Problem();
			p.status = this.status;
			p.type = this.type;
			p.title = this.title;
			p.detail = this.detail;
			return p;
		}
	}
	
	private Problem() {}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
}

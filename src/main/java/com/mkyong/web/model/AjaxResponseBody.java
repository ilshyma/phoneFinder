package com.mkyong.web.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mkyong.web.jsonview.Views;

public class AjaxResponseBody {

	@JsonView(Views.Public.class)
	String msg;
	@JsonView(Views.Public.class)
	String result;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setResult(String result) {
		this.result = result;
	}
	public String getResult() {
		return result;
	}

	@Override
	public String toString() {
		return "AjaxResponseResult [msg=" + msg + ", result=" + result + "]";
	}

}

package com.example.rest.webServices.restfulwebServices;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginForm {

	@JsonProperty("newHexagon")
	public String newHexagon;

	@JsonProperty("neighbor")
	public String name;

	@JsonProperty("sharedEdgeNeighbor")
	public int boundary;

	public void setNewHexagon(String newHexagon) {
		this.newHexagon = newHexagon;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBoundary(int boundary) {
		this.boundary = boundary;
	}

	public String getNewHexagon() {
		return newHexagon;
	}

	public String getName() {
		return name;
	}

	public int getBoundary() {
		return boundary;
	}

	public LoginForm() {
		// TODO Auto-generated constructor stub
	}

}

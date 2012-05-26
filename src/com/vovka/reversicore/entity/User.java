package com.vovka.reversicore.entity;

import com.vovka.reversicore.communication.CellStatus;

public class User {
	
	public CellStatus type;
	public String name;
	
	public User(String name, CellStatus type) {
		this.name = name;
		this.type = type;
	}
}

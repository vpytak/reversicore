package com.vovka.reversicore.entity;

public class CellPosition {
	public int i;
	public int j;
	public CellPosition(int i, int j) {
		super();
		this.i = i;
		this.j = j;
	}
	public boolean valid() {
		return (i >= 0 && j >= 0);
	}
}

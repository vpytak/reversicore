package com.vovka.reversicore.communication;

import com.vovka.reversicore.entity.Grid;

public class GameStatus {
	private Grid grid;
	private CellStatus activeUserType;
	
	public GameStatus(Grid grid, CellStatus user) {
		this.grid = grid;
		this.activeUserType = user;
	}
	
	public Grid grid() {
		return grid;
	}
	
	public CellStatus user() {
		return activeUserType;
	}
}

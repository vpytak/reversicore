package com.vovka.reversicore.communication;

import com.vovka.reversicore.entity.Grid;

public class GameStatus {
	private Grid grid;
	private CellStatus activeUserType;
	private boolean gameFinished;
	
	public GameStatus(Grid grid, CellStatus user, boolean gameFinished) {
		this.grid = grid;
		this.activeUserType = user;
		this.gameFinished = gameFinished;
	}
	
	public Grid grid() {
		return grid;
	}
	
	public CellStatus user() {
		return activeUserType;
	}
	
	public boolean gameFinished() {
		return gameFinished;
	}
}

package com.vovka.reversicore.communication;

import java.util.ArrayList;
import java.util.List;

import com.vovka.reversicore.IllegalMoveException;
import com.vovka.reversicore.entity.Grid;
import com.vovka.reversicore.entity.User;

public class GameController {
	
	public Grid grid;
	private List<User> users;
	private User user1 = new User("User 1", CellStatus.WHITE);
	private User user2 = new User("User 2", CellStatus.BLACK);
	
	public GameController() {
		
	}
	
	public GameStatus newGame() {
		return newGame(CellStatus.WHITE);
	}
	
	public GameStatus newGame(CellStatus type) {
		users = new ArrayList<User>();
		if (type == CellStatus.WHITE) {
			users.add(user1);
			users.add(user2);
		} else {
			users.add(user2);
			users.add(user1);
		}
		grid = Grid.startGrid();
		return status();
	}
	
	public GameStatus move(int i, int j) {
		try {
			grid = GameEngine.makeMove(getGrid(), i, j, getUser().type);
			users.add(users.get(0));
			users.remove(0);
		} catch (IllegalMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status();
	}
	
	public User getUser() {
		return users.get(0);
	}
	
	public Grid getGrid() {
		return grid.clone();
	}
	
	public GameStatus status() {
		return new GameStatus(getGrid(), getUser().type);
	}
}

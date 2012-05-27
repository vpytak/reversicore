package com.vovka.reversicore.communication;

import com.vovka.reversicore.IllegalMoveException;
import com.vovka.reversicore.OutOfBoundException;
import com.vovka.reversicore.entity.Grid;

public class GameEngine {

	public static Grid makeMove(Grid gridSource, int i, int j, CellStatus type) throws IllegalMoveException {
		Grid grid = gridSource.clone();
		if (i < 0 || i > 7 || j < 0 || j > 7)
			throw new OutOfBoundException("Move out of bounds. (" + i + "," + j + ")");
		
		if (grid.cells[i][j] != CellStatus.EMPTY)
			throw new IllegalMoveException("Cell occupied. (" + i + "," + j + ") is " + grid.cells[i][j] + " though had to be empty");
		
		
		//TODO make reversi algorythm here
		grid.cells[i][j] = type;
		CellStatus oppositeType = CellStatus.WHITE;
		if (type == CellStatus.WHITE)
			oppositeType = CellStatus.BLACK;
		
		boolean effective = false;
		for (int k = -1; k <=1; k++) 
			for (int l = -1; l<=1; l++) {
				int steps = 1;
				while (validSubMove(i, j, k, l, steps)
						&& grid.cells[steps*k + i][steps * l + j] == oppositeType)
					steps++;
				
				if (steps > 1 && validSubMove(i, j, k, l, steps) && grid.cells[steps*k + i][steps * l + j] == type) {
					effective = true;
					for (int z = 1; z < steps; z++)
						grid.cells[z*k + i][z * l + j] = type;
				}
			}
		
		if (!effective)
			throw new IllegalMoveException("Zero effectivity move. Not legal");
		
		return grid;
	}
	
	private static boolean validSubMove(int i, int j, int k, int l, int steps) {
		return (steps * k + i >=0
						&& steps * k + i < 8
						&& steps * l + j >= 0
						&& steps * l + j < 8);
	}

}
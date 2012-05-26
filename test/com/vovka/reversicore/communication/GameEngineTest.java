package com.vovka.reversicore.communication;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vovka.reversicore.IllegalMoveException;
import com.vovka.reversicore.OutOfBoundException;
import com.vovka.reversicore.entity.Grid;

public class GameEngineTest {

	protected Grid contextGrid;
	@Before
	public void setUp() throws Exception {
		int[][] arr = {{0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,1,2,0,0,0},
                {0,0,0,2,1,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}};
		CellStatus[][] cells = new CellStatus[8][8];
		for (int i = 0; i<8; i++) {
			for (int j = 0; j<8; j++) {
				switch (arr[i][j]) {
				case 0:
					cells[i][j] = CellStatus.EMPTY;
					break;
				case 1:
					cells[i][j] = CellStatus.WHITE;
					break;
				case 2:
					cells[i][j] = CellStatus.BLACK;

				default:
					break;
				}
			}
		}
		contextGrid = new Grid(cells);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test (expected=OutOfBoundException.class)
	public void testMakeMoveOutOfBounds() throws IllegalMoveException {
		GameEngine.makeMove(contextGrid, 0, 9, CellStatus.WHITE);
	}
	
	@Test (expected=OutOfBoundException.class)
	public void testMakeMoveOutOfBounds2() throws IllegalMoveException {
		GameEngine.makeMove(contextGrid, 12, 3, CellStatus.WHITE);
	}

}

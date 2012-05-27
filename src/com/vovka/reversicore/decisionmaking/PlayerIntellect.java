package com.vovka.reversicore.decisionmaking;

import com.vovka.reversicore.IllegalMoveException;
import com.vovka.reversicore.communication.CellStatus;
import com.vovka.reversicore.communication.GameEngine;
import com.vovka.reversicore.entity.CellPosition;
import com.vovka.reversicore.entity.Grid;

/*
 * This should realize minimax algorythm basing on evalutaion in GridEvaluator.
 * Currently it does comparing of evalutaions level1 not diving deeper
 */
public class PlayerIntellect {
	private CellStatus type;
	public PlayerIntellect(CellStatus type) {
		this.type = type;
	}
	
	public CellPosition move(Grid grid) throws IllegalMoveException {
		CellPosition res = new CellPosition(0, 0);
		GridEvaluator gridEvaluator = new GridEvaluator();
		Double bestResult = null;
		//Get list of available moves
		for (int i = 0; i< 7; i++) {
			for (int j = 0; j < 7; j++) {
				try {
					Grid moveResult = GameEngine.makeMove(grid.clone(), i, j, type);
					double moveResultValue = gridEvaluator.eval(moveResult, type);
					if (bestResult == null || moveResultValue > bestResult) {
						bestResult = moveResultValue;
						res = new CellPosition(i, j);
					}
				} catch (IllegalMoveException e) {
					//Do nothing
				}
			}
		}
		if (bestResult == null)
			throw new IllegalMoveException("No legal moves");
		
		return res;
	}
}

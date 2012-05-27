package com.vovka.reversicore.decisionmaking;

import com.vovka.reversicore.communication.CellStatus;
import com.vovka.reversicore.entity.Grid;

/**
 * Evaluating status of grid from position of given type.
 * Criteria:
 * 1. Game won - Do not do this yet
 * 2. Corner position occupied
 * 3. Wall position occupied
 * 4. Any position occupied
 * @author vpytak
 *
 */
public class GridEvaluator {
	//Giving weights for criterias
	public static double CORNER_OCCUPIED_WEIGHT = 12;
	public static double WALL_OCCUPIED_WEIGHT = 5;
	public static double ANY_COOUPIED_WEIGHT = 1;
	
	public double eval(Grid grid, CellStatus type) {
		double sum = 0;
		
		//checking corners
		sum += checkCorner(0, 0, grid, type);
		sum += checkCorner(0, 7, grid, type);
		sum += checkCorner(7, 0, grid, type);
		sum += checkCorner(7, 7, grid, type);
		
		//checking walls
		sum += checkHorizontal(0, grid, type);
		sum += checkHorizontal(7, grid, type);
		sum += checkVertical(0, grid, type);
		sum += checkVertical(7, grid, type);
		
		//check rest
		sum += checkAny(grid, type);
		return sum;
	}
	
	private double checkAny(Grid grid, CellStatus type) {
		double res = 0;
		for (int i = 1; i<7; i++)
			for (int j = 1; j<7; j++)
				if (grid.cells[i][j] != CellStatus.EMPTY)
					if (grid.cells[i][j] == type)
						res += WALL_OCCUPIED_WEIGHT;
					else
						res -= WALL_OCCUPIED_WEIGHT;
			
		return res;
	}

	public double checkHorizontal(int i, Grid grid, CellStatus type) {
		double res = 0;
		
		for (int j = 1; j<7; j++) {
			if (grid.cells[i][j] != CellStatus.EMPTY)
				if (grid.cells[i][j] == type)
					res += WALL_OCCUPIED_WEIGHT;
				else
					res -= WALL_OCCUPIED_WEIGHT;
		}
		
		return res;
	}
	
	public double checkVertical(int i, Grid grid, CellStatus type) {
		double res = 0;
		
		for (int j = 1; j<7; j++) {
			if (grid.cells[j][i] != CellStatus.EMPTY)
				if (grid.cells[j][i] == type)
					res += WALL_OCCUPIED_WEIGHT;
				else
					res -= WALL_OCCUPIED_WEIGHT;
		}
		
		return res;
	}
	
	private double checkCorner(int i, int j, Grid grid, CellStatus type) {
		double res = 0;
		if (grid.cells[i][j] != CellStatus.EMPTY)
			if (grid.cells[i][j] == type)
				res += CORNER_OCCUPIED_WEIGHT;
			else
				res -= CORNER_OCCUPIED_WEIGHT;
		
		return res;
	}
}

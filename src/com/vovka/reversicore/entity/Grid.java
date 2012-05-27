package com.vovka.reversicore.entity;

import com.vovka.reversicore.communication.CellStatus;

public class Grid {
	public CellStatus[][] cells;
	
	public static Grid startGrid() {
		CellStatus[][] cells = new CellStatus[8][8];
		for (int i = 0; i<8; i++)
			for(int j = 0; j < 8; j++)
				cells[i][j] = CellStatus.EMPTY;
		cells[3][3] = CellStatus.WHITE;
		cells[4][4] = CellStatus.WHITE;
		cells[3][4] = CellStatus.BLACK;
		cells[4][3] = CellStatus.BLACK;
		
		return new Grid(cells);
	}
	public Grid() {
		
	}
	public Grid(CellStatus[][] cells) {
		this.cells = cells;
	}
	public Grid clone() {
		int rows=cells.length ;
	    
	    //clone the 'shallow' structure of array
	    CellStatus[][] newArray =(CellStatus[][]) cells.clone();
	    //clone the 'deep' structure of array
	    for(int row=0;row<rows;row++){
	        newArray[row]=(CellStatus[]) cells[row].clone();
	    }

	    return new Grid(newArray);
	}
}

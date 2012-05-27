package com.vovka.reversicore.decisionmaking;

import com.vovka.reversicore.entity.Grid;

public class DebugHelper {

	public static void printGrid(Grid grid) {
		System.out.println();
		System.out.println(" abcdefgh");
		for (int i=0; i<8;i++) {
			System.out.print(i+1);
			for (int j = 0; j<8; j++) {
				switch (grid.cells[i][j]) {
				case WHITE:
					System.out.print(1);
					break;
				case BLACK:
					System.out.print(2);
					break;
				default:
					System.out.print(0);
					break;
				}
			}
			System.out.println();
		}
	}
}

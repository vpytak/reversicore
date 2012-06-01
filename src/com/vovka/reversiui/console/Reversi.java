package com.vovka.reversiui.console;

import java.util.Scanner;

import com.vovka.reversicore.IllegalMoveException;
import com.vovka.reversicore.NoLegalMovesException;
import com.vovka.reversicore.communication.CellStatus;
import com.vovka.reversicore.communication.GameController;
import com.vovka.reversicore.communication.GameStatus;
import com.vovka.reversicore.decisionmaking.PlayerIntellect;
import com.vovka.reversicore.entity.CellPosition;

public class Reversi {
	private Scanner in;

	public static void main(String[] args) {
		Reversi reversi = new Reversi();
		reversi.run();
	}

	public void run() {
		in = new Scanner(System.in);
		startGame();
	}

	private void startGame() {
		startMessage();
		PlayerIntellect intellect = new PlayerIntellect(CellStatus.WHITE);
		GameController gameController = new GameController();
		GameStatus status = gameController.newGame();
		
		while (true) {
			displayStatus(status);
			CellPosition move = null;
			
			if (status.user()  == CellStatus.WHITE) {
				try {
					System.out.println("Ask AI to move");
					move = intellect.move(status.grid());
					System.out.println("AI replied: ("+(char)(move.j + 'a')+","+(char)(move.i + '1')+")");
				} catch (IllegalMoveException e) {
					System.out.println("Something helly happened. AI skips turn.");
//					e.printStackTrace();
				} catch (NoLegalMovesException e) {
					System.out.println("AI skips turn because of no legal moves.");
					e.printStackTrace();
				}
			} else {
				String s = "";
				while (s.length() < 1)
					s = in.nextLine().trim();
				
				int k = s.charAt(0) - 'a';
				int l = s.substring(1).trim().charAt(0) - '1';
				move = new CellPosition(l, k);
			}
			
			try {
				status = gameController.move(move.i, move.j);
			} catch (IllegalMoveException e) {
				System.out.println("This move is not possible. Try another one");
			}
		}
	}

	private void startMessage() {
		System.out.println("Hi. You are playing reversi. With yourself. Enter moves in array indexes.");
	}
	
	private void displayStatus(GameStatus status) {
		System.out.println();
		System.out.println(" abcdefgh");
		for (int i=0; i<8;i++) {
			System.out.print(i+1);
			for (int j = 0; j<8; j++) {
				switch (status.grid().cells[i][j]) {
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
		System.out.println("Player " + status.user().name() + " turn.");
	}
	
}

package com.vovka.reversiui.console;

import java.util.Scanner;

import com.vovka.reversicore.communication.GameController;
import com.vovka.reversicore.communication.GameStatus;

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
		GameController gameController = new GameController();
		GameStatus status = gameController.newGame();
		
		while (true) {
			displayStatus(status);
			int i =in.nextInt();
			int j = in.nextInt();
			status = gameController.move(i, j);
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

package com.vovka.reversicore.decisionmaking;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

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
	private CellStatus mainType;
	private final int levelDeep = 3;
	private Logger logger;
	public PlayerIntellect(CellStatus type) {
		this.type = type;
		this.mainType = type;
	}
	
	public CellPosition moveSmart(Grid grid, int level) throws IllegalMoveException {
		return moveSmart(grid, level, type);
	}
	
	public CellPosition moveSmart(Grid grid, int level, CellStatus type) throws IllegalMoveException {
		getLogger().info("moveSmart at level " + level);
		CellPosition res = new CellPosition(0, 0);
		int compModifier = 1;
		if (level % 2 == 0) {
			compModifier = -1;
		}
	
		if (level == levelDeep) {
			return move(grid, compModifier);
		} else 
			getLogger().info("Current level: " + level);
		GridEvaluator gridEvaluator = new GridEvaluator();
		
		//for all available moves call movesmart with resulting grid and level +1 
		//then select max (compModifier into notice)
		Double bestResult = null;
		for (int i = 0; i< 7; i++) {
			for (int j = 0; j < 7; j++) {
				try {
					Grid moved = GameEngine.makeMove(grid.clone(), i, j, type);
					getLogger().info("Calling movesmart for level " + (level+1)+" with ("+i+","+j+")");
					CellPosition moveSmart = moveSmart(moved, level + 1, antiType(type));
					getLogger().info("Movesmart for level " + (level+1)+" with ("+i+","+j+") returned i-" + moveSmart.i + ", j-" + moveSmart.j);
					Grid makeMove = GameEngine.makeMove(moved, moveSmart.i, moveSmart.j, antiType(type));
					double moveResultValue = gridEvaluator.eval(makeMove, mainType);
					
					if (bestResult == null || compModifier * moveResultValue > compModifier * bestResult) {
						bestResult = moveResultValue;
						res = new CellPosition(i, j);
					} 
				} catch (IllegalMoveException e) {
					//Do nothing
				}
			}
		}
		getLogger().info("Retruning: i-" + res.i + ", j-" + res.j);
		return res;
	}
	
	public CellPosition moveSmart(Grid grid) throws IllegalMoveException {
		getLogger().info("Entry into AI");
		return moveSmart(grid, 1);
	}
	
	public CellPosition move(Grid grid) throws IllegalMoveException {
		return move(grid, 1);
	}
	
	public CellPosition move(Grid grid, int compModifier) throws IllegalMoveException {
		return move(grid, compModifier, type);
	}
	
	public CellPosition move(Grid grid, int compModifier, CellStatus type) throws IllegalMoveException {
		CellPosition res = new CellPosition(0, 0);
		GridEvaluator gridEvaluator = new GridEvaluator();
		Double bestResult = null;
		//Get list of available moves
		for (int i = 0; i< 7; i++) {
			for (int j = 0; j < 7; j++) {
				try {
					Grid moveResult = GameEngine.makeMove(grid.clone(), i, j, type);
					double moveResultValue = gridEvaluator.eval(moveResult, mainType);
					if (bestResult == null || compModifier * moveResultValue > compModifier * bestResult) {
						bestResult = moveResultValue;
						res = new CellPosition(i, j);
					}
				} catch (IllegalMoveException e) {
					//Do nothing
				}
			}
		}
		if (bestResult == null) {
			getLogger().info("Shit happened");
			DebugHelper.printGrid(grid);
			throw new IllegalMoveException("No legal moves");
		}
		
		return res;
	}
	
	private CellStatus antiType(CellStatus type) {
		if (type == CellStatus.WHITE)
			return CellStatus.BLACK;
		if (type == CellStatus.BLACK)
			return CellStatus.WHITE;
		return CellStatus.EMPTY;
	}
	
	public Logger getLogger() {
		if (logger == null) {
			try {
		      LogManager lm = LogManager.getLogManager();
		      ConsoleHandler h = new ConsoleHandler();

		      logger = Logger.getLogger("LoggingExample1");

		      lm.addLogger(logger);
		      logger.setLevel(Level.OFF);

		      logger.addHandler(h);
		      
		    } catch (Exception e) {
		      System.out.println("Exception thrown: " + e);
		      e.printStackTrace();
		    }
		}
		return logger;
	}
}

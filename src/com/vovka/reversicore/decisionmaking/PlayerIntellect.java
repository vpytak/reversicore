package com.vovka.reversicore.decisionmaking;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.vovka.reversicore.IllegalMoveException;
import com.vovka.reversicore.NoLegalMovesException;
import com.vovka.reversicore.communication.CellStatus;
import com.vovka.reversicore.communication.GameEngine;
import com.vovka.reversicore.entity.CellPosition;
import com.vovka.reversicore.entity.Grid;

/*
 * This should realize minimax algorithm basing on evaluation in GridEvaluator.
 */
public class PlayerIntellect {
	
	/**
	 * Type of AI
	 */
	private CellStatus mainType;
	
	/**
	 * level of investigation
	 */
	private final int levelDeep = 3;
	
	private Logger logger;
	
	public PlayerIntellect(CellStatus type) {
		this.mainType = type;
	}
	
	public CellPosition move(Grid grid) throws IllegalMoveException, NoLegalMovesException {
		getLogger().info("Entry into AI");
		return move(grid, 1);
	}
	
	public CellPosition move(Grid grid, int level) throws IllegalMoveException, NoLegalMovesException {
		return move(grid, level, mainType);
	}
	
	public CellPosition move(Grid grid, int level, CellStatus type) throws IllegalMoveException, NoLegalMovesException {
		getLogger().info("move at level " + level);
		CellPosition res = new CellPosition(-1, -1);
		int compModifier = 1;
		if (level % 2 == 0) {
			compModifier = -1;
		}

		GridEvaluator gridEvaluator = new GridEvaluator();
		
		//for all available moves call move with resulting grid and level +1 
		//then select max (compModifier into notice)
		Double bestResult = null;
		for (int i = 0; i< 7; i++) {
			for (int j = 0; j < 7; j++) {
				try {
					Grid initialMove = GameEngine.makeMove(grid.clone(), i, j, type);
					double moveResultValue = 0;
					if (level == levelDeep) {
						moveResultValue = gridEvaluator.eval(initialMove, mainType);
					} else {
						getLogger().info("Calling move for level " + (level+1)+" with ("+i+","+j+")");
						CellPosition move = move(initialMove, level + 1, antiType(type));
						getLogger().info("Move for level " + (level+1)+" with ("+i+","+j+") returned i-" + move.i + ", j-" + move.j);
						Grid conditionalMove = GameEngine.makeMove(initialMove, move.i, move.j, antiType(type));
						moveResultValue = gridEvaluator.eval(conditionalMove, mainType);
					}
					
					if (bestResult == null || compModifier * moveResultValue > compModifier * bestResult) {
						bestResult = moveResultValue;
						res = new CellPosition(i, j);
					} 
				} catch (IllegalMoveException e) {
					//Do nothing - we use this as choosing move list.
				} catch (NoLegalMovesException e) {
					//Do nothing
				}
			}
		}
		if (!res.valid()) 
			throw new NoLegalMovesException("No legal moves");
		
		getLogger().info("Retruning: i-" + res.i + ", j-" + res.j);
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

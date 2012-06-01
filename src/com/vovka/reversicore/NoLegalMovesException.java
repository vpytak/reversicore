package com.vovka.reversicore;

public class NoLegalMovesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoLegalMovesException(String string) {
		super(string);
	}

}

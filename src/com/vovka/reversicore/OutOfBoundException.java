package com.vovka.reversicore;

public class OutOfBoundException extends IllegalMoveException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4407383387229492076L;

	public OutOfBoundException(String string) {
		super(string);
	}

}

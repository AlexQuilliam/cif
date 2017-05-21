package cif.exceptions;

import cif.convenience.Unit;

public class InvalidUnitCodeException extends Exception {
	private static final long serialVersionUID = 856593870596611871L;

	public InvalidUnitCodeException(Unit unit) {
		super("Invalid unit code \'" + unit + "\'");
	}
}

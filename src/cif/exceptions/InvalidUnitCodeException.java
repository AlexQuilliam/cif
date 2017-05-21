package cif.exceptions;

public class InvalidUnitCodeException extends Exception {
	private static final long serialVersionUID = 856593870596611871L;

	public InvalidUnitCodeException(String unitCode) {
		super("Invalid unit code \'" + unitCode + "\'");
	}
}

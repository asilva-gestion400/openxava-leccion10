package com.ayto.multas.as400;

public class LlamadaProgramaAS400Exception extends Exception {

	private static final long serialVersionUID = 1L;

	public LlamadaProgramaAS400Exception() {
		super();
	}

	public LlamadaProgramaAS400Exception(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LlamadaProgramaAS400Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public LlamadaProgramaAS400Exception(String message) {
		super(message);
	}

	public LlamadaProgramaAS400Exception(Throwable cause) {
		super(cause);
	}
}

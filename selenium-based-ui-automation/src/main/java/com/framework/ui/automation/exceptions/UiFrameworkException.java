package com.framework.ui.automation.exceptions;

public class UiFrameworkException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UiFrameworkException() {
		super();
	}

	public UiFrameworkException(String msg) {
		super(msg);
	}
	
	public UiFrameworkException(String message, Throwable cause) {
        super(message, cause);
    }

}

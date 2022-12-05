package com.practical.iapps.exception;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/**
 * XML Exception Handler.
 * 
 * @author Yunus.Patel
 * @see ErrorHandler
 * @see SAXParseException
 * @since 05-December-2022
 * @version 1.0
 *
 */
public class XMLExceptionHandler implements ErrorHandler {

	private List<SAXParseException> exceptions;

	public XMLExceptionHandler() {
		this.exceptions = new ArrayList<>();
	}

	public List<SAXParseException> getExceptions() {
		return exceptions;
	}

	@Override
	public void warning(SAXParseException exception) {
		exceptions.add(exception);
	}

	@Override
	public void error(SAXParseException exception) {
		exceptions.add(exception);
	}

	@Override
	public void fatalError(SAXParseException exception) {
		exceptions.add(exception);
	}
}
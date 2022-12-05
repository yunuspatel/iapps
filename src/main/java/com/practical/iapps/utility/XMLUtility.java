package com.practical.iapps.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.practical.iapps.dto.EpaperRequestDto;
import com.practical.iapps.exception.XMLExceptionHandler;

/**
 * XML utility class for validation and xml to object conversion.
 * 
 * @author Yunus.Patel
 * @since 05-December-2022
 * @version 1.0
 *
 */
@Component
public class XMLUtility {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * Method to initialize xsd validation.
	 * 
	 * @param xsdPath
	 * @return Validator
	 * @throws SAXException
	 */
	private Validator initValidator(String xsdPath) throws SAXException {
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Source schemaFile = new StreamSource(getFile(xsdPath));
		Schema schema = factory.newSchema(schemaFile);
		return schema.newValidator();
	}

	private File getFile(String fileName) {
		return new File(getClass().getClassLoader().getResource(fileName).getFile());
	}

	/**
	 * Method to check whether this provide file is valid or not according to xsd.
	 * 
	 * @param xsdPath
	 * @param xmlPath
	 * @return boolean
	 * @throws IOException
	 * @throws SAXException
	 */
	public boolean isValid(String xsdPath, String xmlPath) throws IOException, SAXException {
		Validator validator = initValidator(xsdPath);
		try {
			validator.validate(new StreamSource(getFile(xmlPath)));
			return true;
		} catch (SAXException e) {
			return false;
		}
	}

	/**
	 * Method used to get which are exceptions generated after xml file validation.
	 * 
	 * @param xsdPath
	 * @param xmlFile
	 * @throws IOException
	 * @throws SAXException
	 */
	public List<SAXParseException> listValidatingExceptionsIfAny(String xsdPath, File xmlFile)
			throws IOException, SAXException {
		XMLExceptionHandler xsdErrorHandler = new XMLExceptionHandler();
		Validator validator = initValidator(xsdPath);
		validator.setErrorHandler(xsdErrorHandler);
		try {
			validator.validate(new StreamSource(xmlFile));
		} catch (SAXParseException e) {
			e.printStackTrace();
			LOGGER.error("Error in listValidatingExceptionsIfAny, " + e.getMessage());
		}
		// xsdErrorHandler.getExceptions().forEach(e -> LOGGER.error(e.getMessage()));
		return xsdErrorHandler.getExceptions();
	}

	/**
	 * Method used to convert xml file into dto objects.
	 * 
	 * @param fileInputStream
	 * @return EpaperRequestDto
	 * @see EpaperRequestDto
	 * @throws JAXBException
	 */
	public EpaperRequestDto buildEpaperRequestXMLObjectFromXMLFile(InputStream fileInputStream) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(EpaperRequestDto.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		EpaperRequestDto epaperRequestDto = (EpaperRequestDto) unmarshaller.unmarshal(fileInputStream);
		return epaperRequestDto;
	}
}
package com.practical.iapps.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

/**
 * Epaper service.
 * 
 * @author Yunus.Patel
 * @since 05-December-2022
 * @version 1.0
 *
 */
public interface EpaperService {

	/**
	 * Method used to upload file and save e-paper data into table.
	 * 
	 * @param request
	 * @param paperXml
	 * @return ResponseEntity
	 * @throws IOException
	 * @throws SAXException
	 * @throws JAXBException
	 */
	ResponseEntity<?> uploadPaper(HttpServletRequest request, MultipartFile paperXml)
			throws IOException, SAXException, JAXBException;

	/**
	 * Method used to get papers data from table according to provided filters in
	 * request.
	 * 
	 * @param request
	 * @param fromDate
	 * @param toDate
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param asc
	 * @param search
	 * @return ResponseEntity
	 */
	ResponseEntity<?> getPapers(HttpServletRequest request, Long fromDate, Long toDate, Integer pageNo,
			Integer pageSize, String sortBy, Boolean asc, String search);

}
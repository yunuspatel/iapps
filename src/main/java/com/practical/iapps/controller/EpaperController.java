package com.practical.iapps.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.practical.iapps.service.EpaperService;

/**
 * Epaper controller class.
 * 
 * @author Yunus.Patel
 * @see EpaperService
 * @since 05-December-2022
 * @version 1.0
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class EpaperController {

	@Autowired
	private EpaperService epaperService;

	@PostMapping("/upload/paper")
	public ResponseEntity<?> uploadPaper(HttpServletRequest request, @RequestParam("paperXml") MultipartFile paperXml)
			throws IOException, SAXException, JAXBException {
		return epaperService.uploadPaper(request, paperXml);
	}

	@GetMapping("/getPapers")
	public ResponseEntity<?> getPapers(HttpServletRequest request,
			@RequestParam(value = "fromDate", required = false) Long fromDate,
			@RequestParam(value = "toDate", required = false) Long toDate,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "asc", defaultValue = "false", required = false) Boolean asc,
			@RequestParam(value = "search", defaultValue = "", required = false) String search) {
		return epaperService.getPapers(request, fromDate, toDate, pageNo, pageSize, sortBy, asc, search);
	}
}
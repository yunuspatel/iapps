package com.practical.iapps.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.practical.iapps.dto.EpaperDto;
import com.practical.iapps.dto.EpaperRequestDto;
import com.practical.iapps.model.Epaper;
import com.practical.iapps.repository.EpaperRepository;
import com.practical.iapps.utility.XMLUtility;

import lombok.RequiredArgsConstructor;

/**
 * Epaper service implementation class.
 * 
 * @author Yunus.Patel
 * @see EpaperRepository
 * @see XMLUtility
 * @since 05-December-2022
 * @version 1.0
 *
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EpaperServiceImpl implements EpaperService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	private final EpaperRepository epaperRepository;
	private final XMLUtility xmlUtility;

	@Transactional
	@Override
	public ResponseEntity<?> uploadPaper(HttpServletRequest request, MultipartFile paperXml)
			throws IOException, SAXException, JAXBException {
		LOGGER.info("uploadPaper called");
		boolean isXmlFile = validateIsXmlFile(paperXml);
		if (isXmlFile) {
			List<SAXParseException> parseExceptions = xmlUtility
					.listValidatingExceptionsIfAny("epaper-schema-definition.xsd", convertToTempFile(paperXml));
			if (parseExceptions == null || parseExceptions.isEmpty()) {
				EpaperRequestDto epaperRequestDto = xmlUtility
						.buildEpaperRequestXMLObjectFromXMLFile(paperXml.getInputStream());
				Epaper epaper = convertToEntity(epaperRequestDto, paperXml.getOriginalFilename());
				epaper = epaperRepository.save(epaper);
				return ResponseEntity.ok(convertToDto(epaper));
			}
			return ResponseEntity.badRequest()
					.body(parseExceptions.stream().map(SAXParseException::getMessage).collect(Collectors.toList()));
		}
		return ResponseEntity.badRequest().body("Invalid file");
	}

	/**
	 * Method used to convert entity class to dto.
	 * 
	 * @param epaper
	 * @see EpaperDto
	 * @return EpaperDto
	 */
	private EpaperDto convertToDto(Epaper epaper) {
		LOGGER.info("convertToDto called");
		EpaperDto epaperDto = new EpaperDto();
		BeanUtils.copyProperties(epaper, epaperDto);
		return epaperDto;
	}

	/**
	 * Method used to build new entity object based on data received.
	 * 
	 * @param epaperRequestDto
	 * @param fileName
	 * @return Epaper
	 * @see Epaper
	 * @see EpaperRequestDto
	 */
	private Epaper convertToEntity(EpaperRequestDto epaperRequestDto, String fileName) {
		LOGGER.info("convertToEntity called");
		Epaper epaper = Epaper.builder().id(null).fileName(fileName)
				.newsPaperName(epaperRequestDto.getDeviceInfo().getAppInfo().getNewspaperName())
				.height(epaperRequestDto.getDeviceInfo().getScreenInfo().getHeight())
				.width(epaperRequestDto.getDeviceInfo().getScreenInfo().getWidth())
				.dpi(epaperRequestDto.getDeviceInfo().getScreenInfo().getDpi()).createdDate(new Date()).build();
		return epaper;
	}

	/**
	 * Method used to validate received xml file.
	 * 
	 * @param paperXml
	 * @return boolean
	 */
	private boolean validateIsXmlFile(MultipartFile paperXml) {
		LOGGER.info("validateIsXmlFile called");
		if (paperXml.getContentType().contains("text/xml") || paperXml.getContentType().contains("application/xml")) {
			return true;
		}
		return false;
	}

	public File convertToTempFile(MultipartFile file) throws IOException {
		Path tempFilePath = Files.createTempFile(file.getName(), ".tmp");
		try (OutputStream os = Files.newOutputStream(tempFilePath)) {
			os.write(file.getBytes());
		}
		return tempFilePath.toFile();
	}

	@Override
	public ResponseEntity<?> getPapers(HttpServletRequest request, Long fromDate, Long toDate, Integer pageNo,
			Integer pageSize, String sortBy, Boolean asc, String search) {
		LOGGER.info("getPapers called");
		Date dateFrom = fromDate == null ? new Date(0) : new Date(fromDate);
		Date dateTo = toDate == null ? new Date() : new Date(toDate);
		Pageable page = PageRequest.of(pageNo == null ? 0 : pageNo,
				pageSize == null ? (Integer.MAX_VALUE - 1) : pageSize == Integer.MAX_VALUE ? (pageSize - 1) : pageSize,
				Sort.by(asc == null || asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy == null ? "id" : sortBy));
		List<Epaper> epapers = epaperRepository.getAllPapersByFilters(page, dateFrom, dateTo, search);
		return ResponseEntity.ok(epapers.stream().map(this::convertToDto).collect(Collectors.toList()));
	}
}
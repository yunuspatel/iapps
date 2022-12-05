package com.practical.iapps;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;

import com.practical.iapps.service.EpaperService;
import com.practical.iapps.utility.XMLUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
class IAppsApplicationTests {

	@Autowired
	private XMLUtility xmlUtility;

	@Autowired
	private EpaperService epaperService;

	@Mock
	HttpServletRequest request;

	@Test
	public void validXml_ThenTrue() throws IOException, SAXException {
		Assert.assertTrue(xmlUtility.isValid("epaper-schema-definition.xsd", "epaper-valid.xml"));
	}

	@Test
	public void invalidXML_ThenFalse() throws IOException, SAXException {
		Assert.assertFalse(xmlUtility.isValid("epaper-schema-definition.xsd", "epaper-invalid.xml"));
	}

	@Test
	public void validSortData_ThenNotNull() throws IOException, SAXException {
		ResponseEntity<?> result = epaperService.getPapers(request, null, null, 0, 10, "id", false, "");
		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assert.assertNotNull(result.getBody());
	}

}
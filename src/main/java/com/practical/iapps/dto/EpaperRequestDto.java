package com.practical.iapps.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "epaperRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "epaperRequest")
public class EpaperRequestDto {

	@XmlElementRef(name = "deviceInfo")
	private DeviceInfo deviceInfo;

	@XmlElementRef(name = "getPages")
	private GetPages getPages;
}
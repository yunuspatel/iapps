package com.practical.iapps.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "deviceInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(EpaperRequestDto.class)
public class DeviceInfo {

	@XmlAttribute
	private String name;

	@XmlAttribute
	private String id;

	@XmlElementRef(name = "screenInfo")
	private ScreenInfo screenInfo;

	@XmlElementRef(name = "osInfo")
	private OsInfo osInfo;

	@XmlElementRef(name = "appInfo")
	private AppInfo appInfo;
}
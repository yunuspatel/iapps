package com.practical.iapps.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Epaper entity class.
 * 
 * @author Yunus.Patel
 * @since 05-December-2022
 * @version 1.0
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "epaper")
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Epaper implements Serializable {

	private static final long serialVersionUID = -8951768357073878263L;

	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "news_paper_name", length = 100)
	private String newsPaperName;

	@Column(nullable = false)
	private Long height;

	@Column(nullable = false)
	private Long width;

	@Column(nullable = false)
	private Long dpi;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date createdDate;

	@Column(name = "file_name", length = 500, unique = true)
	private String fileName;

	@Override
	public String toString() {
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = objectWriter.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
}
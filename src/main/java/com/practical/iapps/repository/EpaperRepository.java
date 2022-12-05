package com.practical.iapps.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practical.iapps.model.Epaper;

/**
 * Epaper JPA repository.
 * 
 * @author Yunus.Patel
 * @see Epaper
 * @since 05-December-2022
 * @version 1.0
 *
 */
@Repository
public interface EpaperRepository extends JpaRepository<Epaper, Long> {

	@Query("select e from Epaper e where "
			+ " (e.newsPaperName like %:search% or e.height like %:search% or e.width like %:search% or e.dpi like %:search% or e.fileName like %:search%) "
			+ " and e.createdDate between :dateFrom AND :dateTo")
	List<Epaper> getAllPapersByFilters(Pageable page, @Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo,
			@Param("search") String search);

}
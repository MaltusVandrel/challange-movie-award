package com.texoit.challenge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.texoit.challenge.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	Movie findByTitle(String title);
	
	List<Movie> findByYear(Integer year);

}

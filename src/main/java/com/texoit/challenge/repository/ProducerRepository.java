package com.texoit.challenge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.texoit.challenge.entity.Producer;

public interface  ProducerRepository  extends JpaRepository<Producer, Long> {
	
	Producer findByName(String name);

	@Query( "SELECT producer "
		  + "FROM Producer producer "
		  + "JOIN producer.movies movie "
		  + "WHERE movie.winner = true "
		  + "ORDER BY producer.id, movie.year")
	List<Producer> getAwardedProducers();
	
}

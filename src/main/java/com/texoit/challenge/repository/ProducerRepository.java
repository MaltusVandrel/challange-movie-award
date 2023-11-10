package com.texoit.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.texoit.challenge.entity.Producer;

public interface  ProducerRepository  extends JpaRepository<Producer, Long> {
	
	Producer findByName(String name);
	
}

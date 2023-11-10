package com.texoit.challenge.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.texoit.challenge.entity.Studio;

public interface StudioRepository extends JpaRepository<Studio, Long> {
	
	Studio findByName(String name);	
	
	
}

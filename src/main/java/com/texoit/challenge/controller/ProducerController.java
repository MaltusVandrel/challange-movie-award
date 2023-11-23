package com.texoit.challenge.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.texoit.challenge.service.ProducerService;

@RestController
@RequestMapping("/producer")
public class ProducerController {
	
	@Autowired
	private ProducerService service;
	
	@GetMapping("/awarded-producers") 
	public ResponseEntity getProducerAwardMinAndMaxInterval() {
		try {
			return new ResponseEntity<Map>(service.getProducerAwardMinAndMaxInterval(),HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 

}

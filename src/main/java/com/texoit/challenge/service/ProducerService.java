package com.texoit.challenge.service;

import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.texoit.challenge.entity.Movie;
import com.texoit.challenge.entity.Producer;
import com.texoit.challenge.repository.ProducerRepository;
import com.texoit.challenge.util.CSVUtil;

@Service
public class ProducerService {
	Logger logger = LoggerFactory.getLogger(ProducerService.class);
	
	@Autowired
	private ProducerRepository dao;
	
	public void saveProducers(List<String> row,Movie movie) {
		String producers=row.get(CSVUtil.COLUMN_STUDIOS);
		for (String name : producers.split(CSVUtil.LIST_DELIMITER)) {
			name=name.trim();
			Producer producer = new Producer(name);

			Example<Producer> example = Example.of(producer); 

			if (dao.exists(example)) {
				producer = dao.findByName(name);
			} else {
				producer = dao.save(producer);
			}
			if(producer.getMovies()==null) {
				producer.setMovies(new HashSet<Movie>());
			}
			producer.getMovies().add(movie);
			dao.save(producer);
		}
	}
}

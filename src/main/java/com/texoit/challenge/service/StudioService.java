package com.texoit.challenge.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.texoit.challenge.entity.Movie;
import com.texoit.challenge.entity.Studio;
import com.texoit.challenge.repository.StudioRepository;
import com.texoit.challenge.util.CSVUtil;

@Service
public class StudioService {
	Logger logger = LoggerFactory.getLogger(StudioService.class);
	
	@Autowired
	private StudioRepository dao;
	
	public void saveStudios(List<String> row,Movie movie) {
		String studios=row.get(CSVUtil.COLUMN_STUDIOS);
		for (String name : studios.split(CSVUtil.LIST_DELIMITER)) {
			name=name.trim();
			Studio studio = new Studio(name);

			Example<Studio> example = Example.of(studio); 

			if (dao.exists(example)) {
				studio = dao.findByName(name);
			} else {
				studio = dao.save(studio);
			}
			if(studio.getMovies()==null) {
				studio.setMovies(new ArrayList<Movie>());
			}
			studio.getMovies().add(movie);
			dao.save(studio);
		}
	}
}

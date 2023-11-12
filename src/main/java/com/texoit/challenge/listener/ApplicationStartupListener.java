package com.texoit.challenge.listener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.texoit.challenge.entity.Movie;
import com.texoit.challenge.service.MovieService;
import com.texoit.challenge.service.ProducerService;
import com.texoit.challenge.service.StudioService;
import com.texoit.challenge.util.CSVUtil;

@Component
public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private MovieService serviceMovie;
	@Autowired
	private ProducerService serviceProducer;
	@Autowired
	private StudioService serviceStudio;
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream in = cl.getResourceAsStream(CSVUtil.DEFAULT_MOVIELIST_FILE);
		List<List<String>> rows = CSVUtil.toRows(in);
		
		for (List<String> row : rows) {
			Movie movie = serviceMovie.insertAllFromRow(row);
			serviceProducer.saveProducers(row, movie);
			serviceStudio.saveStudios(row, movie);
		}	
	
	}
	
	
	
	
}

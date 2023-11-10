package com.texoit.challenge.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.texoit.challenge.entity.Movie;
import com.texoit.challenge.repository.MovieRepository;
import com.texoit.challenge.util.CSVUtil;

@Service
public class MovieService {
	Logger logger = LoggerFactory.getLogger(MovieService.class);
	
	@Autowired
	private MovieRepository dao;
	
	public Movie insertAllFromRow(List<String> columns) {
		String title=columns.get(CSVUtil.COLUMN_TITLE);
		Integer year=Integer.parseInt(columns.get(CSVUtil.COLUMN_YEAR));
		Boolean winner=isRowEntryWinner(columns);
		Movie movie = new Movie(title,year,winner);
		return dao.save(movie);
	}
	private Boolean isRowEntryWinner(List<String> columns){
		if(columns.size()<CSVUtil.COLUMN_NUMBER_IN_A_FILLED_ROW) {
			//se não tiver a coluna contedo a informação de vencedor, é considerado como não vencedor
			return Boolean.FALSE;
		}else {
			return CSVUtil.WORD_FOR_TRUE.equals(columns.get(CSVUtil.COLUMN_WINNER));
		}
	}
	public List<Movie> findAll() {
		return dao.findAll();
	}
}

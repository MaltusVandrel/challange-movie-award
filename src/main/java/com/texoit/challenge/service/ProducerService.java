package com.texoit.challenge.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.texoit.challenge.dto.ProducerAwardIntervalDTO;
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
		String producers=row.get(CSVUtil.COLUMN_PRODUCERS);
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
				producer.setMovies(new ArrayList<Movie>());
			}
			producer.getMovies().add(movie);
			dao.save(producer);
		}
	}

	public Map getProducerAwardMinAndMaxInterval() {
		Map<String,List<ProducerAwardIntervalDTO>> minAndMaxInterval=new HashMap<String, List<ProducerAwardIntervalDTO>>();
		List<Producer> awardedProducers = dao.getAwardedProducers();
		List<ProducerAwardIntervalDTO> producerAwardIntervals = fromAwardedProducersToIntervals(awardedProducers);
		List<ProducerAwardIntervalDTO> minProducerAwardIntervals = getMinProducerAwardIntervals(producerAwardIntervals);
		List<ProducerAwardIntervalDTO> maxProducerAwardIntervals = getMaxProducerAwardIntervals(producerAwardIntervals);
		minAndMaxInterval.put("min", minProducerAwardIntervals);
		minAndMaxInterval.put("max", maxProducerAwardIntervals);		
		return minAndMaxInterval;
		
	}
	private List<ProducerAwardIntervalDTO> getMinProducerAwardIntervals(
			List<ProducerAwardIntervalDTO> producerAwardIntervals) {
		List<ProducerAwardIntervalDTO> minIntervals=new ArrayList<ProducerAwardIntervalDTO>();
		Integer interval= Integer.MAX_VALUE;
		for (ProducerAwardIntervalDTO producerAwardIntervalDTO : producerAwardIntervals) {
			if(producerAwardIntervalDTO.getInterval()<interval) {
				interval=producerAwardIntervalDTO.getInterval();
				minIntervals=new ArrayList<ProducerAwardIntervalDTO>();
				minIntervals.add(producerAwardIntervalDTO);	
			}else if(producerAwardIntervalDTO.getInterval()==interval) {
				minIntervals.add(producerAwardIntervalDTO);	
			}
		}
		return minIntervals;
	}
	private List<ProducerAwardIntervalDTO> getMaxProducerAwardIntervals(
			List<ProducerAwardIntervalDTO> producerAwardIntervals) {
		List<ProducerAwardIntervalDTO> maxIntervals=new ArrayList<ProducerAwardIntervalDTO>();
		Integer interval= Integer.MIN_VALUE;
		for (ProducerAwardIntervalDTO producerAwardIntervalDTO : producerAwardIntervals) {
			if(producerAwardIntervalDTO.getInterval()>interval) {
				interval=producerAwardIntervalDTO.getInterval();
				maxIntervals=new ArrayList<ProducerAwardIntervalDTO>();
				maxIntervals.add(producerAwardIntervalDTO);	
			}else if(producerAwardIntervalDTO.getInterval()==interval) {
				maxIntervals.add(producerAwardIntervalDTO);	
			}
		}
		return maxIntervals;
	}

	private List<ProducerAwardIntervalDTO> fromAwardedProducersToIntervals(List<Producer> awardedProducers){
		List<ProducerAwardIntervalDTO> producerAwardIntervals = new ArrayList<ProducerAwardIntervalDTO>();
		for (Producer producer : awardedProducers) {
			List<Movie> movies= producer.getMovies();
			List<Movie> wins =new ArrayList<Movie>();
			for (Movie movie : movies) {
				if(BooleanUtils.isTrue(movie.getWinner()))wins.add(movie);
			}
			if(wins.size()>=2) {
				producerAwardIntervals.addAll(createProduceAwardIntervals(producer,wins));
			}			
		}
		return producerAwardIntervals;
	}
	private List<ProducerAwardIntervalDTO> createProduceAwardIntervals(Producer producer, List<Movie> winners){
		List<ProducerAwardIntervalDTO> intervals = new ArrayList<ProducerAwardIntervalDTO>();
		for (int i=1;i<winners.size();i++) {
			Movie previousWin=winners.get(i-1);
			Movie followingWin=winners.get(i);
			intervals.add(new ProducerAwardIntervalDTO(producer.getName(), followingWin.getYear()-previousWin.getYear(), previousWin.getYear(), followingWin.getYear()));
		}
		return intervals;
	}
}

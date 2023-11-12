package com.texoit.challenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import com.texoit.challenge.entity.Movie;
import com.texoit.challenge.entity.Producer;
import com.texoit.challenge.entity.Studio;
import com.texoit.challenge.repository.MovieRepository;
import com.texoit.challenge.repository.ProducerRepository;
import com.texoit.challenge.repository.StudioRepository;
import com.texoit.challenge.util.CSVUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StartupDataIntegrationTest {
	
	private List<List<String>> validRows;
	
	@Autowired
	private MovieRepository repositoryMovie;

	@Autowired
	private ProducerRepository repositoryProducer;
	
	@Autowired
	private StudioRepository repositoryStudio;
	
	@Before
	public void  setup() {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream in = cl.getResourceAsStream(CSVUtil.DEFAULT_MOVIELIST_FILE);
		this.validRows = CSVUtil.toRows(in);
	}
	
	@Test
	public void hasCVSDataLoadedToDatabase() {
		List<Movie> movies=repositoryMovie.findAll();		
		assertEquals(this.validRows.size(),movies.size());	
	}
	
	@Test
	public void areMoviesFromCSVInDatabase() {
		int maxSampleSize=10;
		if(maxSampleSize>this.validRows.size())maxSampleSize=this.validRows.size();
		for(int i=0;i<maxSampleSize;i++) {
			Movie movie=new Movie();
			movie.setTitle(this.validRows.get(i).get(CSVUtil.COLUMN_TITLE));
			assertTrue(repositoryMovie.exists(Example.of(movie)));
		}		
	}
	
	@Test
	public void areProducerFromCSVInDatabase() {
		int maxSampleSize=10;
		if(maxSampleSize>this.validRows.size())maxSampleSize=this.validRows.size();
		for(int i=0;i<maxSampleSize;i++) {
			String producersOfMovie=this.validRows.get(i).get(CSVUtil.COLUMN_PRODUCERS);
			String[] producerNames=producersOfMovie.split(CSVUtil.LIST_DELIMITER);
			for (String producerName : producerNames) {
				Producer producer=new Producer();
				producer.setName(producerName.trim());
				assertTrue(repositoryProducer.exists(Example.of(producer)));
			}
			
		}		
	}
	
	@Test
	public void areStudioFromCSVInDatabase() {
		int maxSampleSize=10;
		if(maxSampleSize>this.validRows.size())maxSampleSize=this.validRows.size();
		for(int i=0;i<maxSampleSize;i++) {
			String studiosOfMovie=this.validRows.get(i).get(CSVUtil.COLUMN_STUDIOS);
			String[] studioNames=studiosOfMovie.split(CSVUtil.LIST_DELIMITER);
			for (String studioName : studioNames) {
				Studio studio=new Studio();
				studio.setName(studioName.trim());
				assertTrue(repositoryStudio.exists(Example.of(studio)));
			}
		}		
	}
}

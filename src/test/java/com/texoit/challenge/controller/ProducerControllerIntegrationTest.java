package com.texoit.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProducerControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getProducerAwardMinAndMaxInterval() throws JsonProcessingException, Exception {
		this.mockMvc.perform(get("/producer/awarded-producers")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.min").isArray())
				.andExpect(jsonPath("$.min").isNotEmpty())
				.andExpect(jsonPath("$.min.[0].producer").value("Joel Silver"))
				.andExpect(jsonPath("$.min.[0].interval").value(1))
				.andExpect(jsonPath("$.min.[0].previousWin").value(1990))
				.andExpect(jsonPath("$.min.[0].followingWin").value(1991))
				.andExpect(jsonPath("$.max").isArray())
				.andExpect(jsonPath("$.max").isNotEmpty())
				.andExpect(jsonPath("$.max.[0].producer").value("Matthew Vaughn"))
				.andExpect(jsonPath("$.max.[0].interval").value(13))
				.andExpect(jsonPath("$.max.[0].previousWin").value(2002))
				.andExpect(jsonPath("$.max.[0].followingWin").value(2015));
	}
}

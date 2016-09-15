package com.apress.spring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.iterableWithSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class SpringBootJournalApplicationTests {

	private MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception{
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void getAll() throws Exception {
		mockMvc.perform(get("/journal/all")).andExpect(status().isOk()).andExpect(content().contentType(mediaType)).andExpect(jsonPath("$", iterableWithSize(4)));
	}

	@Test
	public void contextLoads() {
	}

}

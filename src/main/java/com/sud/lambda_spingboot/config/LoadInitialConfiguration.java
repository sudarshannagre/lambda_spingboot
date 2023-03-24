package com.sud.lambda_spingboot.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sud.lambda_spingboot.pojo.OpcoConfiguration;

@Configuration
public class LoadInitialConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(LoadInitialConfiguration.class);
	
	public Optional<List<OpcoConfiguration>> loadOpcoConfiguration() {
		logger.info("loadOpcoConfiguration() : start at : " + new Date());
		List<OpcoConfiguration> opcoConfigList = new ArrayList<>();
		try {
			JsonNode node = new ObjectMapper()
					.readTree(new ClassPathResource("OpcoConfiguration.json").getInputStream()).get("configuration");
			Iterator<JsonNode> it = node.iterator();

			while (it.hasNext()) {
				OpcoConfiguration opcoConfig = new ObjectMapper().readValue(it.next().toString(),
						OpcoConfiguration.class);
				opcoConfigList.add(opcoConfig);
			}
		} catch (IOException e) {
			logger.error("Exception in loadOpcoConfiguration() while reading the json file");
		}finally {
			logger.info("All Initial Opco Configuration loaded successfully !!!");
		}
		logger.info("OpcoList : "+opcoConfigList);
		logger.info("loadOpcoConfiguration() : end at : " + new Date());
		return Optional.of(opcoConfigList);
	}

}

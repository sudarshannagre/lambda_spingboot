package com.sud.lambda_spingboot.util;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

import com.sud.lambda_spingboot.config.LoadInitialConfiguration;
import com.sud.lambda_spingboot.pojo.OpcoConfiguration;

@Component
public class ExportToGoogleHandler implements Function<String, Object> {

	private static final Logger logger = LoggerFactory.getLogger(ExportToGoogleHandler.class);

	@Autowired
	private LoadInitialConfiguration loadInitialConfiguration;

	@Autowired
	private BuildProperties buildProperties;

	@Override
	public Object apply(String opcoId) { //here opcoId coming from the AWS lambda while creating need to add
		logger.info("ExportToGoogleLambdaHandler : start at : {}", new Date());
		logger.info("******* Project Build Version : {}", buildProperties.getArtifact()+"-"+buildProperties.getVersion()+"  ***********");
		logger.info("************ OpcoId : " + opcoId + " ***********");
		try {

			Optional<List<OpcoConfiguration>> opcoList = loadInitialConfiguration.loadOpcoConfiguration();

			if (Optional.ofNullable(opcoList).isPresent()) {
				OpcoConfiguration actualOpco = opcoList.get().stream()
						.filter(opco -> opco.getOpcoId().equalsIgnoreCase(opcoId)).collect(Collectors.toList()).get(0);

				logger.info("callWriteJsonData() : Given OPCO Details : {}", actualOpco);
				
			} else {
				logger.warn(
						"callWriteJsonData() : Intitial OPCO COnfiguration file was not loaded please check file present or not !!");
			}

			logger.info("callWriteJsonData() : end at : {}", new Date());
			return "success";
		} catch (Exception e) {
			logger.error("Exception in callWriteJsonData() : {} ", e.getMessage());
			return "failed";
		}
	}

}

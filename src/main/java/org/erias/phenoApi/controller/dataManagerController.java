package org.erias.phenoApi.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.erias.phenoApi.model.IndexDoc;
import org.erias.phenoApi.model.Post2CohorteGraphIC;
import org.erias.phenoApi.model.PostCohorteGraphIC;
import org.erias.phenoApi.model.PostFile;
import org.erias.phenoApi.repository.IndexDocRepository;
import org.erias.phenoApi.repository.ThesaurusEnrsemRepository;
import org.erias.phenoApi.service.EntityInGraphService;
import org.erias.phenoApi.service.EsEntityService;
import org.erias.phenoApi.service.ThesaurusEnrsemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class dataManagerController {
	protected static final Logger log = LogManager.getLogger(dataManagerController.class);
	
	@Autowired
	ThesaurusEnrsemRepository thesaurusEnrsemRepository;
	
	@Autowired 
	ThesaurusEnrsemService thesaurusEnrsemService;
	
	@Autowired 
	IndexDocRepository indexDocRepository;
	
	@Autowired
	EntityInGraphService entityInGraphService;
	
	@Autowired
	EsEntityService esEntityService;
	
	@RequestMapping(value = "/dataManager/delete-all",method = RequestMethod.DELETE )
	public void deleteData() {
		log.info("deleting all data");
		indexDocRepository.deleteAll();
		thesaurusEnrsemRepository.deleteAll();
		log.info("done");
	}
	
	@RequestMapping(value = "/dataManager/load",method = RequestMethod.PUT )
	public void loadData(@RequestBody(required=true)	PostFile postfile) throws IOException {
		
		BufferedReader reader = new BufferedReader(
		new InputStreamReader(new ClassPathResource("extract/"+postfile.getFileName()).getInputStream()));
		Set<IndexDoc> indexDocs = reader
				.lines().skip(1).map(l -> l.split(";")).map(splitted -> new IndexDoc(splitted[0]+postfile.getCohortName(), splitted[1],
						splitted[2], splitted[3], splitted[4], splitted[5], splitted[6], postfile.getCohortName()))
				.collect(Collectors.toSet());

		log.info("loading file " + postfile.getFileName());
		// SQLConnector p
		log.info("End loading file " + postfile.getFileName());

		log.info("loading data to db " + postfile.getFileName());

		indexDocRepository.batchLoad(10000, indexDocs);

		log.info("End loading data to db " + postfile.getFileName());
		
		reader.close();
	}
	
	@RequestMapping(value = "/dataManager/metadata/update",method = RequestMethod.PUT )
	public void updateMetadata() throws IOException {
		entityInGraphService.updateGraphMetrics();
		thesaurusEnrsemService.updateThesaurusEnrSem();
		esEntityService.updateEntityRepository();
	}

}

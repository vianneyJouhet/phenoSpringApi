package org.erias.phenoApi.controller;

import java.util.List;
import org.erias.phenoApi.repository.IndexDocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value= "/index-doc")
public class IndexDocController {

	@Autowired
	private IndexDocRepository  indexDocRepo;
	
//	@CrossOrigin(origins = "http://localhost")
	@RequestMapping(value= "/cohorte/list",method = RequestMethod.GET)
	public List<String> listCohorte(){
		return indexDocRepo.findDistinctCohorteAndCount();
	}
}

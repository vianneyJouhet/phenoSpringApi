package org.erias.phenoApi.controller;

import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.erias.phenoApi.model.PostCohorteGraphIC;
import org.erias.phenoApi.service.JQCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JQCloudController {

	protected static final Logger log = LogManager.getLogger(JQCloudController.class);
	
//	private SQLConnector psClient; 
	
	@Autowired
	private JQCloudService jqCloudService;
	
	
	
//	@CrossOrigin(origins = "http://localhost")
	@RequestMapping(value = "/jq-cloud",method = RequestMethod.POST )
	public Map<String,Set<Object>>  getJQCloud(@RequestBody(required=true) String cohorte) {
		
				
		return jqCloudService.getJQCloud(cohorte);
	}
	
	@RequestMapping(value = "/jq-cloud/agg/icSanchez",method = RequestMethod.POST )
	public Map<String,Set<Object>>  getJQCloud(@RequestBody(required=true) PostCohorteGraphIC postCohorteGraphIC) {
				
		return jqCloudService.getJQCloudAggByIcForGraph(postCohorteGraphIC.getCohorte(), postCohorteGraphIC.getIcSanchez(), postCohorteGraphIC.getGraph());
	}
}

package org.erias.phenoApi.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.erias.phenoApi.model.PostCohorteGraphIC;
import org.erias.phenoApi.model.PostUrisGraphIc;
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
	@RequestMapping(value = "/jq-cloud/cohorte",method = RequestMethod.POST )
	public Map<String,Set<Object>>  getJQCloud(@RequestBody(required=true) String cohorte) {
		
				
		return jqCloudService.getJQCloud(cohorte);
	}
	
	@RequestMapping(value = "/jq-cloud/uris",method = RequestMethod.POST )
	public Map<String,Set<Object>>  getJQCloud(@RequestBody(required=true) List<String> uris) {
		log.info(uris.toString());
		return jqCloudService.getJQCloudByCodes(uris);
	}
	
	@RequestMapping(value = "/jq-cloud/hoom/uris",method = RequestMethod.POST )
	public Map<String,Set<Object>>  getJQCloudAggByHoom(@RequestBody(required=true) List<String> uris) {
		log.info(uris.toString());
		return jqCloudService.getJQCloudByCodesAggByHoom(uris);
	}
	
	@RequestMapping(value = "/jq-cloud",method = RequestMethod.POST )
	public Map<String,Set<Object>>  getJQCloud() {
		return jqCloudService.getAllJQCloud();
	}
	
	@RequestMapping(value = "/jq-cloud/agg/icSanchez",method = RequestMethod.POST )
	public Map<String,Set<Object>>  getJQCloud(@RequestBody(required=true) PostCohorteGraphIC postCohorteGraphIC) {
				
		return jqCloudService.getJQCloudAggByIcForGraph(postCohorteGraphIC.getCohorte(), postCohorteGraphIC.getIcSanchez(), postCohorteGraphIC.getGraph());
	}
	
	@RequestMapping(value = "/jq-cloud/agg/icSanchez/uris",method = RequestMethod.POST )
	public Map<String,Set<Object>>  getJQCloud(@RequestBody(required=true) PostUrisGraphIc postUrisGraphIc) {
				
		return jqCloudService.getJQCloudAggByUrisAndIcForGraph(postUrisGraphIc.getUris(), postUrisGraphIc.getIcSanchez(), postUrisGraphIc.getGraph());
	}
}

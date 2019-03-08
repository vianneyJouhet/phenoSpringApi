package org.erias.phenoApi.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.erias.phenoApi.model.CoocurenceMetric;
import org.erias.phenoApi.model.ForceGraphData;
import org.erias.phenoApi.model.ForceLink;
import org.erias.phenoApi.model.ForceNode;
import org.erias.phenoApi.model.Post2CohorteGraphIC;
import org.erias.phenoApi.model.PostCohorteGraphIC;
import org.erias.phenoApi.model.PostCohorteGraphICCode;
import org.erias.phenoApi.service.CoocurenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoococurenceController {

	protected static final Logger log = LogManager.getLogger(CoococurenceController.class);
	
	@Autowired
	private CoocurenceService coocurenceService;

	@RequestMapping(value = "/CoocurenceMetrics",method = RequestMethod.POST )
	public ForceGraphData  getCoocurenceMetrics(@RequestBody(required=true)	PostCohorteGraphIC postGetCoocurence) {
		
		return setForcegraphDataFromcoocurenceMetrics(coocurenceService.getCoocurenceByCohorteAndICInGraph(postGetCoocurence.getCohorte(),postGetCoocurence.getIcSanchez(),postGetCoocurence.getGraph()).computeMetrics());  
	}
	
	@RequestMapping(value = "/CoocurenceMetrics/graphs",method = RequestMethod.POST )
	public ForceGraphData  getCoocurenceMetrics(@RequestBody(required=true)	Post2CohorteGraphIC postGetCoocurence) {
		
		return setForcegraphDataFromcoocurenceMetrics(coocurenceService.getCoocurenceByCohorteAndICInGraphs(postGetCoocurence.getCohorte(),postGetCoocurence.getGraphIc()).computeMetrics());
		  
	}
	
	@RequestMapping(value = "/CoocurenceMetrics/codes",method = RequestMethod.POST )
	public ForceGraphData  getCoocurenceMetrics(@RequestBody(required=true)	PostCohorteGraphICCode postGetCoocurence) {
		log.info(postGetCoocurence.getCohorte() +" " +
						postGetCoocurence.getGraphIc()+" " +
						postGetCoocurence.getUris());
		return setForcegraphDataFromcoocurenceMetrics(
					coocurenceService.getCoocurenceByCohorteAndICInGraphsAndCodes(
						postGetCoocurence.getCohorte(),
						postGetCoocurence.getGraphIc(),
						postGetCoocurence.getUris())
				.computeMetrics());
		  
	}
	
	private ForceGraphData setForcegraphDataFromcoocurenceMetrics(Set<CoocurenceMetric> coocurenceMetrics) {
		Set<ForceNode> forceNodes = coocurenceMetrics.stream()
				.map(m -> new ForceNode(m.getCode1(), m.getGraph1()))
				.collect(Collectors.toSet());
		
		forceNodes.addAll(coocurenceMetrics.stream()
				.map(m -> new ForceNode(m.getCode2(), m.getGraph2()))
				.collect(Collectors.toSet()));
		
		Set<ForceLink> forceLinks = coocurenceMetrics.stream()
				.map(m -> new ForceLink(m.getCode1(), m.getCode2(), m.getNumber()))
				.collect(Collectors.toSet());
		return new ForceGraphData(forceNodes, forceLinks);
	}
}

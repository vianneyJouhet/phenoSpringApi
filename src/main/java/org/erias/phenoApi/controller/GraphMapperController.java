package org.erias.phenoApi.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.erias.phenoApi.service.GraphMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraphMapperController {

	
	private GraphMapper graphMapper;
	
	
	protected static final Logger log = LogManager.getLogger(GraphMapperController.class);
	
	public GraphMapperController(
			@Value("${sparql.protocol}") String protocol,
			@Value("${sparql.port}")int port,
			@Value("${sparql.domain}") String url,
			@Value("${sparql.endpoint}")String namespace) {
		// TODO Auto-generated constructor stub
		
		this.graphMapper = new GraphMapper(protocol, url, port, namespace);
	}
	
	@CrossOrigin(origins = "http://localhost")
	@RequestMapping(value = "/graph-mapping",method = RequestMethod.POST )
	public HashMap<String, Set<String>> getLabelMappings(@RequestBody(required=true) Set<String> uris) {
		log.info("start getLabelMappings");
		if(uris.size()>2000) {
			HashMap<String, Set<String>> labelMappings = new HashMap<String, Set<String>>();
			AtomicInteger idx = new AtomicInteger(0);
			AtomicInteger idx2 = new AtomicInteger(0);
			AtomicInteger totalSize = new AtomicInteger(uris.size());
			Set<String> urisTemps = new HashSet<String>();
			uris.forEach(u -> {
				int j = idx.incrementAndGet();
				urisTemps.add(u);
				if( j%2000 == 0 || j == totalSize.get()) {
					log.info("Exectute batch " + idx2.incrementAndGet());
					graphMapper.getTermInfo(urisTemps.stream()).entrySet().forEach(e -> {
						if(labelMappings.containsKey(e.getKey())){
							Set<String> add =labelMappings.get(e.getKey());
							add.addAll(e.getValue());
							labelMappings.put(e.getKey(),add);
						}else {
							labelMappings.put(e.getKey(), e.getValue());
						}
					});
					
					
					urisTemps.clear();
				}
					
			});
			log.info("Number of codes " + labelMappings.values().stream().flatMap(e -> e.stream()).count());
			return labelMappings;
		}else {
			return graphMapper.getTermInfo(uris.stream());
		}
	}
}

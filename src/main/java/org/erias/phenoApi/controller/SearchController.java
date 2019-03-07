package org.erias.phenoApi.controller;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.jline.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

	protected static final Logger log = LogManager.getLogger(SearchController.class);
	
	@Autowired
	private TransportClient client;
	
	
	@RequestMapping(value = "/search/entity/prefix",method = RequestMethod.GET )
	public Set<Map<String, Object>> searchEntitiesWithPrefix(@RequestParam("term") String term){
			
			 return StreamSupport.stream(client.prepareSearch("entity")
				        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				        .setQuery(QueryBuilders.matchPhrasePrefixQuery("labels.label", term))             // Query
				        .setFrom(0).setSize(60).setExplain(false)
				        .execute()
				        .actionGet().getHits().spliterator(), false)
			 			.map(h -> h.getSourceAsMap())
			 			.collect(Collectors.toSet());
		        
		
	}
	
	@RequestMapping(value = "/search/entity/prefix/graph",method = RequestMethod.GET )
	public Set<Map<String, Object>> searchEntitiesWithPrefixInGraph(@RequestParam("term") String term,@RequestParam("graph") String graph){
			
			log.info(term + " - " + graph);
			
			 return StreamSupport.stream(client.prepareSearch("entity")
				        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				        .setQuery(QueryBuilders.matchPhrasePrefixQuery("prefLabel", term))             // Query
				        .setFrom(0).setSize(60).setExplain(false)
				        .setPostFilter(QueryBuilders.matchPhraseQuery("graph", graph))
				        .execute()	
				        .actionGet().getHits().spliterator(), false)
			 			.map(h -> h.getSourceAsMap())
			 			.collect(Collectors.toSet());
		        
		
	}
}

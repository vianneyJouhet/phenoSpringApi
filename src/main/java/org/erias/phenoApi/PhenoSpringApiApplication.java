package org.erias.phenoApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//import org.apache.solr.client.solrj.io.eval.GetValueEvaluator;
import org.erias.phenoApi.model.CoocurenceMatrix;
import org.erias.phenoApi.model.CoocurenceMetric;
import org.erias.phenoApi.model.Entity;
//import org.erias.phenoApi.model.Entity;
import org.erias.phenoApi.model.EntityHierarchie;
import org.erias.phenoApi.model.EntityInGraph;
import org.erias.phenoApi.model.IndexDoc;
import org.erias.phenoApi.model.IndexDocIdf;
import org.erias.phenoApi.model.ThesaurusEnrsem;
import org.erias.phenoApi.repository.IndexDocIdfRepository;
import org.erias.phenoApi.repository.IndexDocRepository;
import org.erias.phenoApi.repository.ThesaurusEnrsemRepository;
import org.erias.phenoApi.repository.es.EsEntityRepository;
import org.erias.phenoApi.repository.rdf4j.GraphRepository;
import org.erias.phenoApi.service.CoocurenceService;
import org.erias.phenoApi.service.EntityInGraphService;
import org.erias.phenoApi.service.EsEntityService;
import org.erias.phenoApi.service.IndexDocIdfService;
import org.erias.phenoApi.service.JQCloudService;
import org.erias.phenoApi.service.ThesaurusEnrsemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.util.StopWatch;


@SpringBootApplication
public class PhenoSpringApiApplication {

	private static final Logger log = LoggerFactory.getLogger(PhenoSpringApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PhenoSpringApiApplication.class, args);
	}

//	@Bean
//	public  WebMvcConfigurer corsConfigurer () {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("**").allowedOrigins("http://localhost");
//			}
//		};
//	}
//	@Bean
//	public CommandLineRunner demo(IndexDocRepository loader, ThesaurusEnrsemService thesaurusEnrsemServiceImpl,
//			ThesaurusEnrsemRepository thEnrsemLoader, @Value("${sparql.protocol}") String protocol,
//			@Value("${sparql.port}") int port, @Value("${sparql.domain}") String url,
//			@Value("${sparql.endpoint}") String namespace,
//			CoocurenceService coocurenceservice,
//			EntityInGraphService entityInGraphService,
//			ThesaurusEnrsemService thesaurusEnrsemService,
//			GraphRepository graphRepository,
//			IndexDocIdfRepository idfRepository,
//			JQCloudService jqCloudService,
//			EsEntityRepository entRepo,
//			EsEntityService esEntityService) {
//		return (args) -> {
//
//			Long nLignes = loader.countByCohorte("rett");
//			log.info("-------------------------");
//			log.info("Rett ==> " + Long.toString(nLignes));
//			log.info("-------------------------");
//			if (nLignes.intValue() == 0) {
////				BufferedReader reader = new BufferedReader(
////						new FileReader(new File("src/main/resources/extract/resultsIndexRett.csv")));
//				BufferedReader reader = new BufferedReader(
//						new InputStreamReader(new ClassPathResource("extract/resultsIndexRett2.csv").getInputStream()));
//
//				log.info("loading file Rett");
//				// SQLConnector psClient= new PostgresClient(Config.POSTGRES_HOST,
//				// Config.POSTGRES_DB, Config.POSTGRES_PORT, Config.POSTGRES_USER,
//				// Config.POSTGRES_PASSWORD);
//				// Set<IndexDoc> indexDocs = new IndexDocDao(psClient).getAllIndexDoc();
//
//				Set<IndexDoc> indexDocs = reader
//						.lines().skip(1).map(l -> l.split(";")).map(splitted -> new IndexDoc(splitted[0]+"rett", splitted[1],
//								splitted[2], splitted[3], splitted[4], splitted[5], splitted[6], "rett"))
//						.collect(Collectors.toSet());
//
//				reader.close();
//
//				log.info("End loading file Rett");
//
//				log.info("loading data to db Rett");
//
//				loader.batchLoad(10000, indexDocs);
//
//				log.info("End loading data to db Rett");
//			} else {
//				log.info("Rett already exists");
//			}
//			nLignes = loader.countByCohorte("Russel");
//			log.info("-------------------------");
//			log.info("Russel ==> " + Long.toString(nLignes));
//			log.info("-------------------------");
//			if (nLignes.intValue() == 0) {
////				BufferedReader reader = new BufferedReader(
////						new FileReader(new File("src/main/resources/extract/resultsIndexRett.csv")));
//				BufferedReader reader = new BufferedReader(
//						new InputStreamReader(new ClassPathResource("extract/resultsIndexRussell.csv").getInputStream()));
//
//				log.info("loading file reussle");
//				// SQLConnector psClient= new PostgresClient(Config.POSTGRES_HOST,
//				// Config.POSTGRES_DB, Config.POSTGRES_PORT, Config.POSTGRES_USER,
//				// Config.POSTGRES_PASSWORD);
//				// Set<IndexDoc> indexDocs = new IndexDocDao(psClient).getAllIndexDoc();
//
//				Set<IndexDoc> indexDocs = reader
//						.lines().skip(1).map(l -> l.split(";")).map(splitted -> new IndexDoc(splitted[0]+"Russel", splitted[1],
//								splitted[2], splitted[3], splitted[4], splitted[5], splitted[6], "Russel"))
//						.collect(Collectors.toSet());
//
//				reader.close();
//
//				log.info("End loading file Russel");
//
//				log.info("loading data to db Russel");
//
//				loader.batchLoad(10000, indexDocs);
//
//				log.info("End loading data to db Russel");
//			} else {
//				log.info("Russel already exists");
//			}
//			nLignes = loader.countByCohorte("ramdom");
//			log.info("-------------------------");
//			log.info("Ramdom ==> " + Long.toString(nLignes));
//			log.info("-------------------------");
//			if (nLignes.intValue() == 0) {
//				BufferedReader reader = new BufferedReader(
//						new InputStreamReader(new ClassPathResource("extract/resultsIndexRamdom2.csv").getInputStream()));
//
//				log.info("loading file Ramdom");
//				// SQLConnector psClient= new PostgresClient(Config.POSTGRES_HOST,
//				// Config.POSTGRES_DB, Config.POSTGRES_PORT, Config.POSTGRES_USER,
//				// Config.POSTGRES_PASSWORD);
//				// Set<IndexDoc> indexDocs = new IndexDocDao(psClient).getAllIndexDoc();
//
//				Set<IndexDoc> indexDocs = reader
//						.lines().skip(1).map(l -> l.split(";")).map(splitted -> new IndexDoc(splitted[0]+"ramdom", splitted[1],
//								splitted[2], splitted[3], splitted[4], splitted[5], splitted[6], "ramdom"))
//						.collect(Collectors.toSet());
//
//				reader.close();
//
//				log.info("End loading file Ramdom");
//
//				log.info("loading data to db Ramdom");
//
//				loader.batchLoad(10000, indexDocs);
//
//				log.info("End loading data to db Ramdom");
//			} else {
//				log.info("Ramdom already exists");
//			}
//
//	
//			nLignes = thEnrsemLoader.count();
//			log.info("-------------------------");
//			log.info("ENRSEM present ? ==> " + Long.toString(nLignes));
//			log.info("-------------------------");
////			entityInGraphService.updateGraphMetrics();
////			thesaurusEnrsemServiceImpl.updateThesaurusEnrSem();
////			esEntityService.updateEntityRepository();
//			if (nLignes.intValue() == 0) {
//
////				LabelMapper labelMapper = new LabelMapper(protocol, url, port, namespace);
//				log.info("Loading ENRSEM");
////				List<ThesaurusFrequency> thesaurusFrequencies = thesaurusEnrsemServiceImpl.loadThesaurusFromIndexDoc();
//				
//				
//
//				
//				//				HashMap<String, String> labels = labelMapper.getPrefLabelForUrisBatch(
////						thesaurusFrequencies.stream().map(ThesaurusFrequency::getCode).collect(Collectors.toSet()));
////
//////				Long nbPat = loader.countDistinctPatientNum();
////				log.info("Nombre de patient total " + nbPat);
////				log.info("Number of codes ==> " + thesaurusFrequencies.size());
////				log.info("Number of labels ==> " + labels.size());
//
////				thEnrsemLoader.batchLoad(10000,
////						thesaurusFrequencies.stream()
////								.map(t -> new ThesaurusEnrsem(t.getCode(), t.getFrequency(), labels.get(t.getCode()),nbPat))
////								.collect(Collectors.toSet()));
//				log.info("END Loading ENRSEM");
//			}else {
//				log.info("ENRSEM already exists");
//			}
//			log.info("-------------------------");
//			log.info("test Coocurence ");
//			log.info("-------------------------");
//
////			Entity test = new Entity("http://test", "test");
////			entityRepo.save(test);
////			List<IndexDocIdf> indexDocs =  idfRepository.findByCertaintyAndContextAndCohorte("1", "patient_text","rett");
////			indexDocs.forEach(i -> {
////				log.info(i.toString());
////			});
////			Set<String> test = new HashSet<>();
////			test.add("http://erias.org/hpo/cismef");
////			test.add("<http://erias.org/umls/phenotype>");
////			log.info(Integer.toString(
////					entityInGraphService.getAllEntityInGraph(test).size()));
////			String graph = "http://erias.org/hpo/cismef";
//
//			
////			HashSet<String> test =new HashSet<>();
////			test.add("http://purl.obolibrary.org/obo/HP_0001250");
////			test.add("http://purl.obolibrary.org/obo/HP_0030871");
////			test.add("http://purl.obolibrary.org/obo/HP_0002650");
////			test.add("http://purl.obolibrary.org/obo/HP_0001252");
////			test.add("http://purl.obolibrary.org/obo/HP_0002493");
//			
////			String graph = "http://erias.org/hpo";
////			Set<String> test = entityInGraphService.getEntityByIcSanchezLessThanInGraph(graph, Double.parseDouble("0.2"))
////					.stream()
////					.map(v -> v.getUri())
////					.collect(Collectors.toSet())
////					;
////			StopWatch sw2 = new StopWatch();
////			sw2.start("Total");
////			
////			coocurenceservice.getCoocurenceByCohorteAndICInGraph("rett", Double.parseDouble("0.5"), graph)
////			.computeMetrics()
////			.stream()
////			.forEach(c -> {
////				log.info(c.toString());
////			});
////			sw2.stop();
//			
//
////			sw2.start("Total-idf");
////			jqCloudService.getJQCloudAggByIcForGraph("rett",Double.parseDouble("0.7"), graph)
////			.get("tab")
////			.forEach(th -> {
////				log.info(th.toString());
////			})
////			;
////			sw2.stop();
////			
////			log.info(sw2.prettyPrint());
//			
////					entityInGraphService.getEntityByIcSanchezLessThanInGraph(graph, Double.parseDouble("0.2"))
////					.stream()
////					.map(v -> v.getUri())
//					
////			Set<String> test = thEnrsemLoader.findByIcSanchezLessThan(Double.parseDouble("0.7"))
////					.stream()
////					.map(ThesaurusEnrsem::getCode)
////					.collect(Collectors.toSet()); 
////					
////			log.info(Integer.toString(test.size()));	
//			
//
////			
////			thEnrsemLoader.findByCodeIn(test).stream().forEach(t -> {
////				log.info(t.toString());
////			});
////			EntityHierarchie h = new EntityHierarchie();
////			h.addAndGetEntityHierarchie("pere", "fils1")
////				.addAndGetEntityHierarchie("pere", "fils2")
////				.addAndGetEntityHierarchie("pere1", "fils3");;
////			
////			log.info("AllPeres ==> " + h.getAllAncestors().toString());
////			log.info("Allfils ==> " + h.getAllChilds().toString());
////			log.info("pere1 ==> " + h.getAncestors("fils1"));
////			log.info("pere2 ==> " + h.getAncestors("fils2"));
////			log.info("pere3 ==> " + h.getAncestors("fils3"));
////			log.info("fils ==> " + h.getChilds("pere"));
////			log.info("fils1 ==> " + h.getChilds("pere1"));
//		
////			Set<CoocurenceMetric> coocurenceMetrics= coocurenceservice.getCoocurenceMericsByCohorte("rett",test);
////			coocurenceMetrics.forEach( x ->{
////				log.info(x.toString());	
////			});
////			log.info(Integer.toString(coocurenceMetrics.size()));
//			
////			Float t = (float) Math.log(0+1);
////			Float u = (float) Math.log(100);
////			Float v = (float) t/u;
////			log.info(Float.toString(t));
////			log.info(Float.toString(u));
////			log.info(Float.toString(v));
////			log.info(Float.toString((float) -Math.log(t)));
////			ICFeatureRepositoryImpl featureService = new ICFeatureRepositoryImpl(protocol, url, port, namespace);
////			HashMap<String, ICFeature> icFeature = featureService.getALLICFeaturesByGraph("http://erias.org/hpo");
////			Long maxLeaves = icFeature
////					.entrySet()
////					.stream()
////					.map(t -> t.getValue())
////					.map(t -> t.getLeaves())
////					.max((t1,t2) -> Long.compare(t1, t2))
////					.get();
////			
////			
////			icFeature.entrySet().forEach(icf -> {
////				InformationContent ic = new InformationContent(icf.getValue(), maxLeaves);
////				log.info(ic.toString());
////			});
//
//		};
//	}
}

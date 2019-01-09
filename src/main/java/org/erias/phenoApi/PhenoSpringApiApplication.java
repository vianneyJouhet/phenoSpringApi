package org.erias.phenoApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.erias.phenoApi.model.IndexDoc;
import org.erias.phenoApi.model.ThesaurusEnrsem;
import org.erias.phenoApi.model.ThesaurusFrequency;
import org.erias.phenoApi.repository.IndexDocLoader;
import org.erias.phenoApi.repository.IndexDocRepository;
import org.erias.phenoApi.repository.ThesaurusEnrsemLoader;
import org.erias.phenoApi.repository.ThesaurusEnrsemRepository;
import org.erias.phenoApi.service.LabelMapper;
import org.erias.phenoApi.service.ThesaurusEnrsemService;
import org.erias.phenoApi.service.ThesaurusEnrsemServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class PhenoSpringApiApplication {

	private static final Logger log = LoggerFactory.getLogger(PhenoSpringApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PhenoSpringApiApplication.class, args);
	}

	@Bean
	public  WebMvcConfigurer corsConfigurer () {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/").allowedOrigins("http://localhost","http://10.144.210.194");
			}
		};
	}
	@Bean
	public CommandLineRunner demo(IndexDocRepository loader, ThesaurusEnrsemService thesaurusEnrsemServiceImpl,
			ThesaurusEnrsemRepository thEnrsemLoader, @Value("${sparql.protocol}") String protocol,
			@Value("${sparql.port}") int port, @Value("${sparql.domain}") String url,
			@Value("${sparql.endpoint}") String namespace) {
		return (args) -> {

			Long nLignes = loader.countByCohorte("rett");
			log.info("-------------------------");
			log.info("Rett ==> " + Long.toString(nLignes));
			log.info("-------------------------");
			if (nLignes.intValue() == 0) {
//				BufferedReader reader = new BufferedReader(
//						new FileReader(new File("src/main/resources/extract/resultsIndexRett.csv")));
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new ClassPathResource("extract/resultsIndexRett.csv").getInputStream()));

				log.info("loading file Rett");
				// SQLConnector psClient= new PostgresClient(Config.POSTGRES_HOST,
				// Config.POSTGRES_DB, Config.POSTGRES_PORT, Config.POSTGRES_USER,
				// Config.POSTGRES_PASSWORD);
				// Set<IndexDoc> indexDocs = new IndexDocDao(psClient).getAllIndexDoc();

				Set<IndexDoc> indexDocs = reader
						.lines().skip(1).map(l -> l.split(";")).map(splitted -> new IndexDoc(splitted[0], splitted[1],
								splitted[2], splitted[3], splitted[4], splitted[5], splitted[6], "rett"))
						.collect(Collectors.toSet());

				reader.close();

				log.info("End loading file Rett");

				log.info("loading data to db Rett");

				loader.batchLoad(10000, indexDocs);

				log.info("End loading data to db Rett");
			} else {
				log.info("Rett already exists");
			}
			nLignes = loader.countByCohorte("ramdom");
			log.info("-------------------------");
			log.info("Ramdom ==> " + Long.toString(nLignes));
			log.info("-------------------------");
			if (nLignes.intValue() == 0) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new ClassPathResource("extract/resultsIndexRamdom.csv").getInputStream()));

				log.info("loading file Ramdom");
				// SQLConnector psClient= new PostgresClient(Config.POSTGRES_HOST,
				// Config.POSTGRES_DB, Config.POSTGRES_PORT, Config.POSTGRES_USER,
				// Config.POSTGRES_PASSWORD);
				// Set<IndexDoc> indexDocs = new IndexDocDao(psClient).getAllIndexDoc();

				Set<IndexDoc> indexDocs = reader
						.lines().skip(1).map(l -> l.split(";")).map(splitted -> new IndexDoc(splitted[0], splitted[1],
								splitted[2], splitted[3], splitted[4], splitted[5], splitted[6], "ramdom"))
						.collect(Collectors.toSet());

				reader.close();

				log.info("End loading file Ramdom");

				log.info("loading data to db Ramdom");

				loader.batchLoad(10000, indexDocs);

				log.info("End loading data to db Ramdom");
			} else {
				log.info("Ramdom already exists");
			}

	
			nLignes = thEnrsemLoader.count();
			log.info("-------------------------");
			log.info("ENRSEM present ? ==> " + Long.toString(nLignes));
			log.info("-------------------------");
			if (nLignes.intValue() == 0) {

				LabelMapper labelMapper = new LabelMapper(protocol, url, port, namespace);
				log.info("Loading ENRSEM");
				List<ThesaurusFrequency> thesaurusFrequencies = thesaurusEnrsemServiceImpl.loadThesaurusFromIndexDoc();

				HashMap<String, String> labels = labelMapper.getPrefLabelForUrisBatch(
						thesaurusFrequencies.stream().map(ThesaurusFrequency::getCode).collect(Collectors.toSet()));

				Long nbPat = loader.countDistinctPatientNum();
				log.info("Nombre de patient total " + nbPat);
				log.info("Number of codes ==> " + thesaurusFrequencies.size());
				log.info("Number of labels ==> " + labels.size());

				thEnrsemLoader.batchLoad(10000,
						thesaurusFrequencies.stream()
								.map(t -> new ThesaurusEnrsem(t.getCode(), t.getFrequency(), labels.get(t.getCode()),nbPat))
								.collect(Collectors.toSet()));
				log.info("END Loading ENRSEM");
			}else {
				log.info("ENRSEM already exists");
			}
			log.info(thEnrsemLoader.findById("http://umls/cui/C0577559").toString());

		};
	}
}

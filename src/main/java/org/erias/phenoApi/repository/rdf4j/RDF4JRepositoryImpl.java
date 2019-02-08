package org.erias.phenoApi.repository.rdf4j;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.query.BindingSet;
import fr.chu.bordeaux.RDF4JClient.services.RDF4JClient;

public class RDF4JRepositoryImpl implements RDF4JRepository {
	private String sparqlEndpoint;
	
	
	protected static final Logger log = LogManager.getLogger(RDF4JRepositoryImpl.class);
	
	public RDF4JRepositoryImpl(String protocol, String url, int port, String namespace) {
		// TODO Auto-generated constructor stub
		try {
			this.setSparqlEndpoint(new URL(protocol, url, port, namespace).toExternalForm());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/* (non-Javadoc)
	 * @see org.erias.phenoApi.repository.rdf4j.RDF4JRepository#getSparqlEndpoint()
	 */
	
//	public String getSparqlEndpoint() {
//		return sparqlEndpoint;
//	}

	/* (non-Javadoc)
	 * @see org.erias.phenoApi.repository.rdf4j.RDF4JRepository#setSparqlEndpoint(java.lang.String)
	 */
	
	private void setSparqlEndpoint(String sparqlEndpoint) {
		this.sparqlEndpoint = sparqlEndpoint;
	}
	
	@Override
	public String toUriList(Set<String> uris) {
		return toUriList(uris.stream());
	}
	
	@Override
	public String toUriList(Stream<String> uris) {
		return uris
				.map(u -> toUri(u))
				.reduce("", String::concat);
	}
	
	@Override
	public String toUri(String uri) {
		return "<"  + uri.replaceAll(">", "").replaceAll("<", "") + ">";
	}
	
	@Override
	public List<BindingSet> execSelectQuery(String query) {
		RDF4JClient client = new RDF4JClient(this.sparqlEndpoint);
		
//		log.info(query);
		
		List<BindingSet> res = client.execTupleQuery(query);
		log.info("Number of BindingSet retrieved : " + res.size());
		client.close();
		return res;
	}
	
	@Override
	public void remove(String subject,String predicate,String object,String graph) {
		RDF4JClient client = new RDF4JClient(this.sparqlEndpoint);
		ValueFactory f =client.getFactory();
		IRI iriSubject = (subject == null)?null:f.createIRI(subject);
		IRI iriPredicate = (predicate == null)?null:f.createIRI(predicate);
		IRI iriObject = (object == null)?null:f.createIRI(object);
		IRI iriGraph = (graph == null)?null:f.createIRI(graph);
		
		
		client.getConnection().remove(iriSubject,iriPredicate,iriObject,iriGraph);
		
//		client.getConnection().remove(createStatement(subject, predicate, object, graph));
		
		client.close();
	}
	
	@Override
	public void addLiteral(String subject,String predicate,String object,String graph) {
		RDF4JClient client = new RDF4JClient(this.sparqlEndpoint);
//		ValueFactory f =client.getFactory();
//		IRI iriSubject = (subject == null)?null:f.createIRI(subject);
//		IRI iriPredicate = (predicate == null)?null:f.createIRI(predicate);
//		Value objectValue = (object == null)?null:f.createLiteral(object);
//		IRI iriGraph = (graph == null)?null:f.createIRI(graph);
//		
//		
//		client.getConnection().add(iriSubject,iriPredicate,objectValue,iriGraph);
		client.getConnection().add(createLiteralStatement(subject, predicate, object, graph));
		client.close();
	}


	@Override
	public void add(String subject, String predicate, String object, String graph) {
		RDF4JClient client = new RDF4JClient(this.sparqlEndpoint);
//		ValueFactory f =client.getFactory();
//		IRI iriSubject=subject.equals(null)?null:f.createIRI(subject);
//		IRI iriPredicate= predicate.equals(null)?null:f.createIRI(predicate);
//		IRI iriObject=object.equals(null)?null:f.createIRI(object);
//		IRI iriGraph=graph.equals(null)?null:f.createIRI(graph);
//		client.getConnection().add(iriSubject,iriPredicate,iriObject,iriGraph);
		
		client.getConnection().add(createStatement(subject, predicate, object, graph));
		client.close();
	}


	@Override
	public void removeLiteral(String subject, String predicate, String object, String graph) {
		RDF4JClient client = new RDF4JClient(this.sparqlEndpoint);
//		ValueFactory f =client.getFactory();
//		IRI iriSubject=subject.equals(null)?null:f.createIRI(subject);
//		IRI iriPredicate= predicate.equals(null)?null:f.createIRI(predicate);
//		Value objectValue=object.equals(null)?null:f.createLiteral(object);
//		IRI iriGraph=graph.equals(null)?null:f.createIRI(graph);
//		
//		Statement st = client.getFactory().createStatement(iriSubject, iriPredicate, objectValue, iriGraph); 
//		client.getConnection().remove(iriSubject,iriPredicate,objectValue,iriGraph);
		
		client.getConnection().remove(createLiteralStatement(subject, predicate, object, graph));
		
		client.close();	
	}
	
	protected Statement createStatement(String subject, String predicate, String object, String graph) {
		RDF4JClient client = new RDF4JClient(this.sparqlEndpoint);
		ValueFactory f =client.getFactory();
		client.close();
		IRI iriSubject=subject.equals(null)?null:f.createIRI(subject);
		IRI iriPredicate= predicate.equals(null)?null:f.createIRI(predicate);
		IRI iriObject=object==null?null:f.createIRI(object);
		IRI iriGraph=graph.equals(null)?null:f.createIRI(graph);
		return client.getFactory().createStatement(iriSubject, iriPredicate, iriObject, iriGraph); 
		
	}
	
	protected Statement createLiteralStatement(String subject, String predicate, String object, String graph) {
		RDF4JClient client = new RDF4JClient(this.sparqlEndpoint);
		ValueFactory f =client.getFactory();
		client.close();
		IRI iriSubject=subject.equals(null)?null:f.createIRI(subject);
		IRI iriPredicate= predicate.equals(null)?null:f.createIRI(predicate);
		Value objectValue=object==null?f.createLiteral("null"):f.createLiteral(object);
		IRI iriGraph=graph.equals(null)?null:f.createIRI(graph);
		return client.getFactory().createStatement(iriSubject, iriPredicate, objectValue, iriGraph);
	}
	
	protected Statement createLiteralDoubleStatement(String subject, String predicate, Double object, String graph) {
		RDF4JClient client = new RDF4JClient(this.sparqlEndpoint);
		ValueFactory f =client.getFactory();
		client.close();
		IRI iriSubject=subject.equals(null)?null:f.createIRI(subject);
		IRI iriPredicate= predicate.equals(null)?null:f.createIRI(predicate);
		Value objectValue=object==null?null:f.createLiteral(object);
		IRI iriGraph=graph.equals(null)?null:f.createIRI(graph);
		return client.getFactory().createStatement(iriSubject, iriPredicate, objectValue, iriGraph);
	}
	
	public void add(Set<Statement> sts,int size) {
		RDF4JClient client = new RDF4JClient(this.sparqlEndpoint);

		
		if (sts.size()>size){
			Set<Statement> stTemp = new HashSet<Statement>();
			AtomicInteger idx = new AtomicInteger(0);
			AtomicInteger idx2 = new AtomicInteger(0);
			AtomicInteger totalSize = new AtomicInteger(sts.size());
			sts.forEach(u -> {
				int j = idx.incrementAndGet();
				stTemp.add(u);
				if( j%size == 0 || j == totalSize.get()) {
					log.info("Exectute batch " + idx2.incrementAndGet());
					client.getConnection().add(stTemp);
					stTemp.clear();
				}
			});
		}else {
			client.getConnection().add(sts);
		}
		
		client.close();
	}
	
	
}

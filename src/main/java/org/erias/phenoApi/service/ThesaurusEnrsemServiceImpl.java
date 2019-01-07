package org.erias.phenoApi.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.erias.phenoApi.model.ThesaurusFrequency;
import org.erias.phenoApi.repository.ThesaurusEnrsemRepository;
import org.erias.phenoApi.repository.ThesaurusFrequencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThesaurusEnrsemServiceImpl implements ThesaurusEnrsemService {
	
	protected static final Logger log = LogManager.getLogger(ThesaurusEnrsemServiceImpl.class);

	
	@Autowired
	private ThesaurusFrequencyRepository thesaurusFrequencyRepository;
	
	/* (non-Javadoc)
	 * @see org.erias.phenoApi.service.ThesaurusEnrsemService#loadThesaurusFromIndexDoc()
	 */
	@Override
	public List<ThesaurusFrequency> loadThesaurusFromIndexDoc() {
		log.info("get code aggregate " +thesaurusFrequencyRepository);
		
		return thesaurusFrequencyRepository.findCodeCount();
	}

}

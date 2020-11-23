package com.jimzrt.RezeptMeister;

import javax.persistence.*;
import javax.transaction.*;

import org.hibernate.search.jpa.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.*;


@Component
public class LuceneInit implements ApplicationListener<ContextRefreshedEvent> {

	@PersistenceContext
	private EntityManager entityManager;
	

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		try {
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			System.out.println("Error occured trying to build Hibernate Search indexes "
					+ e.toString());
		}
	}
	

}
package fr.isika.cda14.efund.repositories;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.isika.cda14.efund.entity.common.Statistics;

@Stateless
public class StatisticsRepository {

	@PersistenceContext
	EntityManager em;

	public Statistics getStats() {
		String query = "SELECT stats FROM Statistics stats";
		return em.createQuery(query, Statistics.class).setMaxResults(1).getSingleResult();
	}

	public void updateStat(Statistics stats) {
		em.merge(stats);
	}
}

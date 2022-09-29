package fr.isika.cda14.efund.repositories;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.isika.cda14.efund.entity.project.Donation;

@Stateless
public class DonationRepository {

	@PersistenceContext
	private EntityManager em;

	public void createDonationRepo(Donation donation) {
		em.persist(donation);
	}
}

package fr.isika.cda14.efund.repositories;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.isika.cda14.efund.entity.account.UserMessage;

@Stateless
public class UserMessageRepository {

	@PersistenceContext
	private EntityManager em;

	public void persist(UserMessage userMessage) {
		em.persist(userMessage);
	}
}

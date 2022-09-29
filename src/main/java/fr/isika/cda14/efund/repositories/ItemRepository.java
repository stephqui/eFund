package fr.isika.cda14.efund.repositories;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.isika.cda14.efund.entity.shop.Item;

@Stateless
public class ItemRepository {

	@PersistenceContext
	private EntityManager em;

	public void create(Item item) {
		em.persist(item);
	}
}

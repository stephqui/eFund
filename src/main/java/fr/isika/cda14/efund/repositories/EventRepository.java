package fr.isika.cda14.efund.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.isika.cda14.efund.entity.common.ContentBlock;
import fr.isika.cda14.efund.entity.project.Event;
import fr.isika.cda14.efund.entity.project.EventRegistration;
import fr.isika.cda14.efund.entity.project.FaqElement;

@Stateless
public class EventRepository {

	@PersistenceContext
	private EntityManager em;

	public void create(Event event) {
		em.persist(event);
	}

	public List<Event> findAll() {
		List<Event> events;
		events = em.createQuery("SELECT e FROM Event e", Event.class).getResultList();
		return events;
	}

	public List<Event> getAllPublishedEvent() {
		List<Event> events;
		events = em.createQuery(
				"SELECT e FROM Event e WHERE e.projectStatus = fr.isika.cda14.efund.entity.enums.ProjectStatus.PUBLISHED",
				Event.class).getResultList();
		return events;
	}

	// recherche d'un event à partir d'un id
	public Event find(Long id) {
		return em.find(Event.class, id);
	}

	// HEAVY LOADER - Fetch les collections d'objets
	public Event loadProjectWithChildren(Long eventId) {
		// On force le Fetching de la collection de ContentBlocks dans l'event
		String query = "SELECT distinct event " + "FROM Event event " + "LEFT JOIN FETCH event.contentBlocks "
				+ "WHERE event.id=:id";
		Event event = em.createQuery(query, Event.class).setParameter("id", eventId).getSingleResult();

		// On force le Fetching de la collection d'élements FAQ dans l'event
		query = "SELECT event " + "FROM Event event " + "LEFT JOIN FETCH event.faq " + "WHERE event=:event";
		event = em.createQuery(query, Event.class).setParameter("event", event).getSingleResult();
		return event;
	}

	// recherche d'un event par son nom à partir de la page EventList
	public List<Event> searchEventFromPage(String searchEvent) {
		String query = "SELECT eventName FROM Event eventName "
				+ "WHERE eventNameprojectStatus = fr.isika.cda14.efund.entity.enums.ProjectStatus.PUBLISHED "
				+ "AND eventName.name LIKE CONCAT('%', :searchEvent, '%') ";
		return em.createQuery(query, Event.class).setParameter("searchEvent", searchEvent).getResultList();
	}

	public void remove(Event event) {
		em.remove(event);
	}

	public void update(Event myEvent) {
		em.merge(myEvent);
	}

	public List<Event> getTopEvents() {
		String query = "SELECT event FROM Event event ORDER BY event.creationDate";
		return em.createQuery(query, Event.class).setMaxResults(3).getResultList();
	}

	public List<Event> getOrgsEvents(Long orgSpaceId) {
		String query = "SELECT events FROM OrganizationSpace os JOIN os.events events WHERE os.id=:id";
		return em.createQuery(query, Event.class).setParameter("id", orgSpaceId).getResultList();
	}

	/* ContentBlock Methods */
	public ContentBlock findBlock(Long blockId) {
		return em.find(ContentBlock.class, blockId);
	}

	public void removeBlock(ContentBlock block) {
		em.remove(block);
	}

	/* FAQ methods */
	public FaqElement findFaq(Long faqId) {
		return em.find(FaqElement.class, faqId);
	}

	public void removeFaq(FaqElement faqElement) {
		em.remove(faqElement);
	}

	public void update(EventRegistration eventRegistration) {
		em.persist(eventRegistration);
		
	}
}

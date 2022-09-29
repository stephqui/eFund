package fr.isika.cda14.efund.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import fr.isika.cda14.efund.entity.project.Event;
import fr.isika.cda14.efund.services.EventService;

@ManagedBean
public class EventListBean {

	@Inject
	private EventService eventService;

	private String searchRequest;

	private List<Event> eventsList;

	public void onLoad(String name) {
		if (name.isEmpty()) {
			this.eventsList = getAllEvents();
		} else {
			this.eventsList = searchResult(name);
		}
	}

	private List<Event> getAllEvents() {
		return this.eventService.getAllPublishedEvents();
	}

	public List<Event> getEvents() {
		return eventsList;
	}

	public String search() {

		String returnUrl = "eventList.xhtml?faces-redirect=true&amp;searchId=" + this.searchRequest;
		return returnUrl;
	}

	public List<Event> searchResult(String searchEvent) {
		return eventService.searchEventFromPage(searchEvent);

	}

	public void setEvents(List<Event> events) {
		this.eventsList = events;
	}

	public String getSearchRequest() {
		return searchRequest;
	}

	public void setSearchRequest(String searchRequest) {
		this.searchRequest = searchRequest;
	}

}

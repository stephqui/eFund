package fr.isika.cda14.efund.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.isika.cda14.efund.entity.account.OrganizationAccount;
import fr.isika.cda14.efund.entity.account.UserAccount;
import fr.isika.cda14.efund.entity.common.ContentBlock;
import fr.isika.cda14.efund.entity.enums.ProjectStatus;
import fr.isika.cda14.efund.entity.project.Event;
import fr.isika.cda14.efund.entity.project.EventRegistration;
import fr.isika.cda14.efund.entity.project.FaqElement;
import fr.isika.cda14.efund.repositories.AccountRepository;
import fr.isika.cda14.efund.repositories.EventRepository;
import fr.isika.cda14.efund.tool.SessionTool;
import fr.isika.cda14.efund.viewmodel.ContentVM;
import fr.isika.cda14.efund.viewmodel.FaqVM;

@Stateless
public class EventService {

	@Inject
	private EventRepository eventRepo;

	@Inject
	private AccountRepository accountRepo;

	@Inject
	StatisticsService statsService;

	public List<Event> getAllEvents() {
		return eventRepo.findAll();
	}

	/* Find on primary key (FAST) */
	public Event findEvent(Long id) {
		return eventRepo.find(id);
	}

	/* HEAVY LOADER => Fetch les collections en LazyLoading */
	public Event loadProjectWithChildren(Long eventId) {
		return eventRepo.loadProjectWithChildren(eventId);
	}

	public List<Event> searchEventFromPage(String searchEvent) {
		return eventRepo.searchEventFromPage(searchEvent);
	}

	public void deleteEvent(Long id) {
		Event event = findEvent(id);
		eventRepo.remove(event);
		statsService.removeEventFromStats();
	}

	public void update(Event myEvent) {
		eventRepo.update(myEvent);
	}

	public List<Event> getTopEvents() {
		return eventRepo.getTopEvents();
	}

	public OrganizationAccount getOrgFromEvent(Long id) {
		return accountRepo.getOrgFromEvent(id);
	}

	public List<Event> getOrgsEvents(Long orgSpaceId) {
		return eventRepo.getOrgsEvents(orgSpaceId);
	}

	/* Content Blocks Methods */
	public void addContent(ContentVM content, Event event) {
		ContentBlock newBlock = new ContentBlock();

		newBlock.setContent(content.getContent());
		newBlock.setType(content.getType());
		event.getContentBlocks().add(newBlock);
		eventRepo.update(event);
	}

	public void removeBlock(Long blockId) {
		ContentBlock block = eventRepo.findBlock(blockId);
		eventRepo.removeBlock(block);
	}

	public void changeStatusToSubmit(Long id) {
		Event event = eventRepo.find(id);
		event.setProjectStatus(ProjectStatus.SUBMITTED);
		eventRepo.update(event);
	}

	public void addVolunteer(Long id) {
		EventRegistration eventRegistration = new EventRegistration();
		
		UserAccount userAccount = accountRepo.findUser(SessionTool.getUserId());
		Event event = eventRepo.find(id);
		event.setVolunteerCurrent(event.getVolunteerCurrent() + 1);
		
		eventRegistration.setEvent(event);
		eventRegistration.setUserSpace(userAccount.getUserSpace());
		eventRepo.update(eventRegistration);

		statsService.addVolunteerToStats();
	}

	public void addFaq(FaqVM faqVM, Event event) {
		FaqElement newFaqElement = new FaqElement();

		newFaqElement.setQuestion(faqVM.getQuestion());
		newFaqElement.setAnswer(faqVM.getAnswer());
		event.getFaq().add(newFaqElement);
		eventRepo.update(event);
	}

	public void removeFaq(Long faqId) {
		FaqElement faqElement = eventRepo.findFaq(faqId);
		eventRepo.removeFaq(faqElement);
	}

	public List<Event> getAllPublishedEvents() {
		return eventRepo.getAllPublishedEvent();
	}
}

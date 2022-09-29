package fr.isika.cda14.efund.managedbeans;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import fr.isika.cda14.efund.entity.account.OrganizationAccount;
import fr.isika.cda14.efund.entity.enums.Role;
import fr.isika.cda14.efund.entity.project.Event;
import fr.isika.cda14.efund.services.EventService;
import fr.isika.cda14.efund.services.InteractionService;
import fr.isika.cda14.efund.tool.FileUpload;
import fr.isika.cda14.efund.tool.SessionTool;
import fr.isika.cda14.efund.viewmodel.ContentVM;
import fr.isika.cda14.efund.viewmodel.FaqVM;

@ManagedBean
@ViewScoped
public class EventPageBean {

	@Inject
	private EventService eventService;

	@Inject
	private InteractionService interactionService;

	private Event event;

	private OrganizationAccount organizationAccount;

	private ContentVM contentVM = new ContentVM();

	private FaqVM faqVM = new FaqVM();

	private Long remainingDays;

	private Long registerDuration;

	public void onLoad(String id) {
		Long eventId = Long.parseLong(id);
		this.event = eventService.loadProjectWithChildren(eventId);
		this.organizationAccount = eventService.getOrgFromEvent(eventId);

		this.remainingDays = calculRemainingDays();
		this.registerDuration = calculRegisterDuration();
	}

	// Calcul de pourcentage / durée / compte à rebour
	public int percentage(Integer volunteerCurrent, Integer volunteerTarget) {
		if (volunteerTarget == 0) {
			return 100;
		}
		return (volunteerCurrent * 100) / volunteerTarget;
	}

	public Long calculRegisterDuration() {
		Date endDate = new Date(this.event.getEndDate().getTime());
		Date startDate = new Date(this.event.getCreationDate().getTime());
		ZonedDateTime endDateTime = ZonedDateTime.ofInstant(endDate.toInstant(), ZoneId.of("UTC"));
		ZonedDateTime startDateTime = ZonedDateTime.ofInstant(startDate.toInstant(), ZoneId.of("UTC"));

		return ChronoUnit.DAYS.between(startDateTime, endDateTime);
	}

	public int countDown(BigDecimal remaining, BigDecimal duration) {
		if (duration.intValue() == 0) {
			return 0;
		}
		return ((remaining.intValue() * 100) / duration.intValue());
	}

	private Long calculRemainingDays() {
		Date endDate = new Date(this.event.getEndDate().getTime());
		ZonedDateTime endDateTime = ZonedDateTime.ofInstant(endDate.toInstant(), ZoneId.of("UTC"));
		Long remainingDays = ChronoUnit.DAYS.between(ZonedDateTime.now(), endDateTime);

		if (remainingDays < 0) {
			remainingDays = 0L;
		}
		return remainingDays;
	}

	/* Méthodes d'interaction User -> Project */

	/* Section Favoris */

	public void addFavorite() {
		interactionService.addFavorite(SessionTool.getUserId(), event);
	}

	public void removeFavorite() {
		interactionService.removeFavorite(SessionTool.getUserId(), event.getId());
	}

	public Boolean isFaved() {
		return interactionService.checkFavorite(SessionTool.getUserId(), event.getId());
	}

	/* Section Likes */

	public void addLike() {
		interactionService.addLike(SessionTool.getUserId(), event);
	}

	public void removeLike() {
		interactionService.removeLike(SessionTool.getUserId(), event.getId());
	}

	public Boolean isLiked() {
		return interactionService.checkLike(SessionTool.getUserId(), event.getId());
	}

	/* Gestion des blocs de contenu dans l'onglet Contenu */

	public Boolean isOfType(String blockType, String tagType) {
		return blockType.equals(tagType);
	}

	public void createBlock(String type) {
		contentVM.setType(type);
		eventService.addContent(contentVM, event);
	}

	public void removeBlock(Long blockId) {
		eventService.removeBlock(blockId);
	}

	// Upload de fichier pour les blocs de contenu
	public void uploadFile(FileUploadEvent uploadEvent) {
		UploadedFile file = uploadEvent.getFile();
		String filePath = "/content/" + file.getFileName();
		contentVM.setContent("img" + filePath);
		FileUpload.doUpload(file, filePath);
	}

	/* Gestion des questions/réponses de la FAQ */

	public void addFaq() {
		eventService.addFaq(faqVM, event);
	}

	public void removeFaq(Long faqId) {
		eventService.removeFaq(faqId);
	}

	/* Update ProjectEntity in EntityManager */
	public void updateEvent() {
		eventService.update(event);
	}

	/* Ajout de volontaires */
	public String addVolunteer(Long id) {
		eventService.addVolunteer(id);
		return "eventPage?faces-redirect=true&includeViewParams=true";
	}

	// Return True si l'utilisateur connecté est le propriétaire de l'organization
	public Boolean isOwner() {
		return this.organizationAccount.getId().equals(SessionTool.getUserId())
				&& SessionTool.getRole().equals(Role.ASSOC.toString());
	}

	/* Getters and Setters */
	public Event getEvent() {
		return event;
	}

	public OrganizationAccount getOrganizationAccount() {
		return organizationAccount;
	}

	public Long getRemainingDays() {
		return remainingDays;
	}

	public EventService getEventService() {
		return eventService;
	}

	public Long getRegisterDuration() {
		return registerDuration;
	}

	public ContentVM getContentVM() {
		return contentVM;
	}

	public FaqVM getFaqVM() {
		return faqVM;
	}

	public void setFaqVM(FaqVM faqVM) {
		this.faqVM = faqVM;
	}
}

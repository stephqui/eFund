package fr.isika.cda14.efund.managedbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import fr.isika.cda14.efund.entity.account.OrganizationAccount;
import fr.isika.cda14.efund.entity.common.ContentBlock;
import fr.isika.cda14.efund.entity.enums.ProjectStatus;
import fr.isika.cda14.efund.entity.project.Event;
import fr.isika.cda14.efund.entity.project.Project;
import fr.isika.cda14.efund.entity.shop.Item;
import fr.isika.cda14.efund.entity.shop.OrderLine;
import fr.isika.cda14.efund.services.AccountService;
import fr.isika.cda14.efund.services.EventService;
import fr.isika.cda14.efund.services.ProjectService;
import fr.isika.cda14.efund.services.ShopService;
import fr.isika.cda14.efund.tool.FileUpload;
import fr.isika.cda14.efund.tool.SessionTool;
import fr.isika.cda14.efund.viewmodel.ContentVM;

@ManagedBean
@ViewScoped
public class OrganizationSpaceBean {
	@Inject
	AccountService accountService;

	@Inject
	ProjectService projectService;

	@Inject
	EventService eventService;

	@Inject
	ShopService shopService;

	ContentVM contentVM = new ContentVM();

	private String orgSpaceImagePath;

	OrganizationAccount orgAccount;

	List<Project> projects = new ArrayList<Project>();
	List<Event> events = new ArrayList<Event>();
	List<Item> items = new ArrayList<Item>();
	List<ContentBlock> blocks = new ArrayList<ContentBlock>();

	private Boolean isOwner;

	/* Loading OrganizationAccount, all inner collections + Init properties */
	public void onLoad(String id) {
		Long orgId = Long.parseLong(id);

		orgAccount = accountService.loadOrganizationAccountWithChildren(orgId);
		projects = orgAccount.getOrganizationSpace().getProjects();
		events = orgAccount.getOrganizationSpace().getEvents();
		items = orgAccount.getOrganizationSpace().getShop().getItems();
		blocks = orgAccount.getOrganizationSpace().getContentBlocks();

		if (orgAccount.getId().equals(SessionTool.getUserId())) {
			isOwner = true;
		} else {
			isOwner = false;
		}
	}

	/* Méthodes de l'onglet Présentation (Content Blocks) */
	// Booléen utilisé pour utiliser la bonne balise en fonction du contenu du bloc
	// Certainement une solution plus simple en EL
	public Boolean isOfType(String blockType, String tagType) {
		return blockType.equals(tagType);
	}

	// Créé un bloc de contenu (tout types confondus)
	public String createBlock(String type) {
		contentVM.setType(type);
		accountService.addContent(contentVM, orgAccount.getOrganizationSpace());
		return "pageOng?faces-redirect=true&includeViewParams=true";
	}

	// Retire un bloc de contenu
	public String removeBlock(Long blockId) {
		accountService.removeBlock(blockId);
		return "pageOng?faces-redirect=true&includeViewParams=true";
	}

	// Upload de fichier pour les blocs de contenu
	public void uploadFile(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		String filePath = "/content/" + file.getFileName();
		contentVM.setContent("img" + filePath);
		FileUpload.doUpload(file, filePath);
	}

	// Upload de fichier pour l'espace Organization et prépare chemin à insérer dans
	// l'organizationSpace
	public void uploadFileForSpace(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		String filePath = "/space/" + file.getFileName();
		FileUpload.doUpload(file, filePath);
		this.orgSpaceImagePath = "/img" + filePath;
	}

	// Mets à jour l'entité
	public String updateOrgSpace() {
		if (orgSpaceImagePath != null) {
			this.orgAccount.getOrganizationSpace().setImagePath(orgSpaceImagePath);
		}
		accountService.updateOrg(orgAccount);
		return "pageOng?faces-redirect=true&includeViewParams=true";
	}

	//
	public OrderLine createOrderLine(Item item) {
		return shopService.createOrderLine(item);
	}

	// Supprime un objet de la boutique
	public String deleteItem(String id) {
		shopService.deleteItem(Long.parseLong(id));
		return "pageOng?faces-redirect=true&includeViewParams=true";
	}

	// Supprime un Projet (DRAFT Only)
	public String deleteProject(String id) {
		projectService.deleteProject(Long.parseLong(id));
		return "pageOng?faces-redirect=true&includeViewParams=true";
	}

	// Supprime un Evénement (DRAFT Only)
	public String deleteEvent(String id) {
		eventService.deleteEvent(Long.parseLong(id));
		return "pageOng?faces-redirect=true&includeViewParams=true";
	}

	// Calcul de pourcentage entre deux BigDecimal
	public int pourcentage(BigDecimal current, BigDecimal target) {
		if (target.intValue() == 0) {
			return 100;
		}
		return (current.intValue() * 100 / target.intValue());
	}

	// Change le statut du projet pour le soumettre à la publication
	public void submitDraftProject(String id) {
		projectService.changeStatusToSubmit(Long.parseLong(id));
	}

	// Change le statut de l'évènement pour le soumettre à la publication
	public void submitDraftEvent(String id) {
		eventService.changeStatusToSubmit(Long.parseLong(id));
	}

	// Vérifie si le projet est en Draft pour cacher/afficher le bouton de
	// suppression
	public Boolean isDraft(ProjectStatus status) {
		return status.equals(ProjectStatus.DRAFT);
	}

	// Getter Setters
	public OrganizationAccount getOrgAccount() {
		return orgAccount;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public List<Event> getEvents() {
		return events;
	}

	public List<Item> getItems() {
		return items;
	}

	public List<ContentBlock> getBlocks() {
		return blocks;
	}

	public ContentVM getContentVM() {
		return contentVM;
	}

	public Boolean getIsOwner() {
		return isOwner;
	}
}

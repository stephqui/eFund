package fr.isika.cda14.efund.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import fr.isika.cda14.efund.services.EventCreationService;
import fr.isika.cda14.efund.tool.FileUpload;
import fr.isika.cda14.efund.viewmodel.EventCreationFormVM;

@ManagedBean
@ViewScoped
public class EventCreationBean {
	
	@Inject
	private EventCreationService eventCreationService;

	private EventCreationFormVM eventCreationFormVM = new EventCreationFormVM();

	public String createEvent(String id) {
		eventCreationService.create(eventCreationFormVM, Long.parseLong(id));
		return "pageOng?faces-redirect=true&includeViewParams=true";
	}
	
	public void uploadFile(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		String filePath = "/event/" + file.getFileName();
		eventCreationFormVM.setImagePath("/img" + filePath);
		FileUpload.doUpload(file, filePath);
	}

	public EventCreationFormVM getEventCreationFormVM() {
		return eventCreationFormVM;
	}

	public void setEventCreationFormVM(EventCreationFormVM eventCreationFormVM) {
		this.eventCreationFormVM = eventCreationFormVM;
	}

}

package fr.isika.cda14.efund.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import fr.isika.cda14.efund.services.ProjectService;
import fr.isika.cda14.efund.tool.FileUpload;
import fr.isika.cda14.efund.viewmodel.ProjectCreationFormVM;

@ManagedBean
@ViewScoped
public class ProjectCreationBean {

	@Inject
	private ProjectService projectCreationService;

	private ProjectCreationFormVM projectCreationFormVM = new ProjectCreationFormVM();

	public String createProject(String orgSpaceId) {
		projectCreationService.create(projectCreationFormVM, Long.parseLong(orgSpaceId));
		return "pageOng?faces-redirect=true&includeViewParams=true";
	}

	public void uploadFile(FileUploadEvent project) {
		UploadedFile file = project.getFile();
		String filePath = "/project/" + file.getFileName();
		projectCreationFormVM.setImagePath("/img" + filePath);
		FileUpload.doUpload(file, filePath);
	}

	public ProjectCreationFormVM getProjectCreationFormVM() {
		return projectCreationFormVM;
	}

	public void setProjectCreationFormVM(ProjectCreationFormVM projectCreationFormVM) {
		this.projectCreationFormVM = projectCreationFormVM;
	}

}

package fr.isika.cda14.efund.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import fr.isika.cda14.efund.services.ShopService;
import fr.isika.cda14.efund.tool.FileUpload;
import fr.isika.cda14.efund.viewmodel.ItemCreationForm;

@ManagedBean
@ViewScoped
public class ItemCreationBean {

	@Inject
	private ShopService creationService;

	private ItemCreationForm itemCreationForm = new ItemCreationForm();

	public String createItem(String shopId) {
		creationService.create(itemCreationForm, Long.parseLong(shopId));
		return "pageOng?faces-redirect=true&includeViewParams=true";
	}

	public void upload(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		String filePath = "/item/" + file.getFileName();
		itemCreationForm.setImagePath("/img" + filePath);
		FileUpload.doUpload(file, filePath);
	}

	public ItemCreationForm getItemCreationForm() {
		return itemCreationForm;
	}

	public void setItemCreationForm(ItemCreationForm itemCreationForm) {
		this.itemCreationForm = itemCreationForm;
	}

}

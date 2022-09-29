package fr.isika.cda14.efund.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import fr.isika.cda14.efund.entity.account.OrganizationAccount;
import fr.isika.cda14.efund.services.AccountService;

@ManagedBean
public class OrganizationsListBean {

	@Inject
	private AccountService organisationService;

	private String searchRequest;

	private List<OrganizationAccount> organizationsList;

	public void onLoad(String name) {
		if (name.isEmpty()) {
			this.organizationsList = getAllOrganizations();
		} else {
			this.organizationsList = searchResult(name);
		}
	}

	public String search() {

		String returnUrl = "orgaListe.xhtml?faces-redirect=true&amp;searchId=" + this.searchRequest;
		return returnUrl;
	}

	public List<OrganizationAccount> searchResult(String searchOrganization) {
		return organisationService.searchOrganizationAccountFromPage(searchOrganization);

	}

	private List<OrganizationAccount> getAllOrganizations() {
		return this.organisationService.findPublishedOrganization();
	}

	public List<OrganizationAccount> getOrganizationsList() {
		return organizationsList;
	}

	public void setOrganizationsList(List<OrganizationAccount> organizationsList) {
		this.organizationsList = organizationsList;
	}

	public String getSearchRequest() {
		return searchRequest;
	}

	public void setSearchRequest(String searchRequest) {
		this.searchRequest = searchRequest;
	}

}

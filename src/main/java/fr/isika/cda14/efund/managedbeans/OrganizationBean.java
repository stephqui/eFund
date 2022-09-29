package fr.isika.cda14.efund.managedbeans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import fr.isika.cda14.efund.entity.account.OrganizationAccount;
import fr.isika.cda14.efund.services.AccountService;

@ManagedBean
public class OrganizationBean {

	@Inject
	private AccountService organisationService;
	private OrganizationAccount orgAccount;
	private String id;

	@PostConstruct
	private void init() {
		orgAccount = getOrganizationAccount(Long.parseLong(id));
	}

	private OrganizationAccount getOrganizationAccount(Long id) {
		return this.organisationService.findOrganizationAccount(id);
	}

	public OrganizationAccount getOrgAccount() {
		return orgAccount;
	}

	public void setOrgAccount(OrganizationAccount orgAccount) {
		this.orgAccount = orgAccount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

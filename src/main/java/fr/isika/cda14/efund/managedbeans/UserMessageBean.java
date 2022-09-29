package fr.isika.cda14.efund.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import fr.isika.cda14.efund.services.UserMessageService;
import fr.isika.cda14.efund.viewmodel.UserMessageViewModel;

@ManagedBean
public class UserMessageBean {

	@Inject
	private UserMessageService userMessageService;

	private UserMessageViewModel userMessage = new UserMessageViewModel();

	public void createMessage() {
		userMessageService.createMessage(userMessage);
	}

	public UserMessageViewModel getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(UserMessageViewModel userMessage) {
		this.userMessage = userMessage;
	}
}

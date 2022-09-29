package fr.isika.cda14.efund.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.isika.cda14.efund.entity.account.UserMessage;
import fr.isika.cda14.efund.repositories.UserMessageRepository;
import fr.isika.cda14.efund.viewmodel.UserMessageViewModel;

@Stateless
public class UserMessageService {

	@Inject
	private UserMessageRepository repo;

	public void createMessage(UserMessageViewModel inputUserMessage) {
		UserMessage userMessager = new UserMessage();

		userMessager.setFirstName(inputUserMessage.getFirstName());
		userMessager.setLastName(inputUserMessage.getLastName());
		userMessager.setEmail(inputUserMessage.getEmail());
		userMessager.setSujet(inputUserMessage.getSujet());
		userMessager.setMessage(inputUserMessage.getMessage());

		repo.persist(userMessager);

	}
}

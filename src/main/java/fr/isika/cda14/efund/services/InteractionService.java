package fr.isika.cda14.efund.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.isika.cda14.efund.entity.account.UserAccount;
import fr.isika.cda14.efund.entity.project.Favorite;
import fr.isika.cda14.efund.entity.project.GenericProject;
import fr.isika.cda14.efund.entity.project.UserLike;
import fr.isika.cda14.efund.repositories.InteractionRepository;

@Stateless
public class InteractionService {
	@Inject
	AccountService accountService;

	@Inject
	ProjectService projectService;

	@Inject
	InteractionRepository repo;

	public void addLike(Long userId, GenericProject project) {
		UserLike like = new UserLike();

		UserAccount user = accountService.findUserAccountById(userId);

		like.setGenericProject(project);
		like.setUserSpace(user.getUserSpace());

		repo.persists(like);
	}

	public void addFavorite(Long userId, GenericProject project) {
		Favorite fav = new Favorite();

		UserAccount user = accountService.findUserAccountById(userId);

		fav.setGenericProject(project);
		fav.setUserSpace(user.getUserSpace());

		repo.persists(fav);
	}

	public Boolean checkFavorite(Long userId, Long projId) {
		UserAccount user = accountService.findUserAccountById(userId);
		List<Favorite> result = repo.checkFavorite(user.getUserSpace().getId(), projId);
		return !result.isEmpty();
	}

	public void removeFavorite(Long userId, Long projId) {
		UserAccount user = accountService.findUserAccountById(userId);
		Favorite fav = repo.getFavorite(user.getUserSpace().getId(), projId);
		repo.remove(fav);
	}

	public Boolean checkLike(Long userId, Long projId) {
		UserAccount user = accountService.findUserAccountById(userId);
		List<UserLike> result = repo.checkLike(user.getUserSpace().getId(), projId);
		return !result.isEmpty();
	}

	/** Supprime un Like à partir d'un UserAccountID et d'un ProjectID **/
	public void removeLike(Long userId, Long projId) {
		UserAccount user = accountService.findUserAccountById(userId);
		UserLike like = repo.getLike(user.getUserSpace().getId(), projId);
		repo.remove(like);
	}
}

package fr.isika.cda14.efund.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.isika.cda14.efund.entity.project.Favorite;
import fr.isika.cda14.efund.entity.project.UserLike;

@Stateless
public class InteractionRepository {

	@PersistenceContext
	EntityManager em;

	/* SECTION FAVORIES */

	public void persists(Favorite fav) {
		em.persist(fav);
	}

	public void remove(Favorite fav) {
		em.remove(fav);
	}

	public List<Favorite> checkFavorite(Long userId, Long projId) {
		String query = "SELECT fav from Favorite fav WHERE fav.userSpace.id=:uid AND fav.genericProject.id=:pid";
		return em.createQuery(query, Favorite.class).setParameter("uid", userId).setParameter("pid", projId)
				.getResultList();
	}

	/* SECTION LIKES */

	public void persists(UserLike like) {
		em.persist(like);
	}

	public void remove(UserLike like) {
		em.remove(like);
	}

	public Favorite getFavorite(Long userId, Long projId) {
		String query = "SELECT fav from Favorite fav WHERE fav.userSpace.id=:uid AND fav.genericProject.id=:pid";
		return em.createQuery(query, Favorite.class).setParameter("uid", userId).setParameter("pid", projId)
				.getSingleResult();

	}

	public List<UserLike> checkLike(Long userId, Long projId) {
		String query = "SELECT lik from UserLike lik WHERE lik.userSpace.id=:uid AND lik.genericProject.id=:pid";
		return em.createQuery(query, UserLike.class).setParameter("uid", userId).setParameter("pid", projId)
				.getResultList();
	}

	public UserLike getLike(Long userId, Long projId) {
		String query = "SELECT lik from UserLike lik WHERE lik.userSpace.id=:uid AND lik.genericProject.id=:pid";
		return em.createQuery(query, UserLike.class).setParameter("uid", userId).setParameter("pid", projId)
				.getSingleResult();
	}
}

package fr.isika.cda14.efund.repositories;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import fr.isika.cda14.efund.entity.account.Account;
import fr.isika.cda14.efund.entity.account.OrganizationAccount;
import fr.isika.cda14.efund.entity.account.UserAccount;
import fr.isika.cda14.efund.entity.common.ContentBlock;
import fr.isika.cda14.efund.entity.project.Donation;
import fr.isika.cda14.efund.entity.project.EventRegistration;
import fr.isika.cda14.efund.entity.project.Favorite;
import fr.isika.cda14.efund.entity.shop.BasketOrder;
import fr.isika.cda14.efund.entity.shop.Shop;
import fr.isika.cda14.efund.entity.space.OrganizationSpace;

@Stateless
public class AccountRepository {

	@PersistenceContext
	private EntityManager em;

	/* Persistance des comptes User */
	public UserAccount persist(UserAccount newUser) {
		em.persist(newUser);
		return newUser;
	}

	/* Persistance des comptes Organization */
	public OrganizationAccount persist(OrganizationAccount newOrg) {
		em.persist(newOrg);
		return newOrg;
	}

	/* Mise à jour des comptes User */
	public void update(UserAccount user) {
		em.merge(user);
	}

	/* Mise à jour des comptes Organization */
	public void update(OrganizationAccount myOrg) {
		em.merge(myOrg);
	}

	/* Recherche d'un compte User à partir d'un ID */
	public UserAccount findUser(Long id) {
		return em.find(UserAccount.class, id);
	}

	/* Recherche d'un compte Organization à partir d'un ID */
	public OrganizationAccount findOrganization(Long id) {
		return em.find(OrganizationAccount.class, id);
	}

	// recherche d'un compte Ornanization par son nom à partir de la page OrgaListe
	public List<OrganizationAccount> searchOrganizationAccountFromPage(String searchOrganization) {
		String query = "SELECT orgName FROM OrganizationAccount orgName JOIN orgName.organizationInfo inf WHERE inf.name LIKE CONCAT('%', :searchOrganization, '%') ";
		return em.createQuery(query, OrganizationAccount.class).setParameter("searchOrganization", searchOrganization)
				.getResultList();
	}

	/* Recherche d'un compte tout type confondu à partir d'un email */
	public Optional<Account> findByEmail(String email) {
		try {
			Optional<Account> result = Optional
					.ofNullable(em.createQuery("SELECT acc FROM Account acc WHERE acc.email=:email ", Account.class)
							.setParameter("email", email).getSingleResult());
			return result;
		} catch (NoResultException ex) {
			ex.printStackTrace();
		}
		return Optional.empty();
	}

	/* Récupération de la liste de toutes les Organizations */
	public List<OrganizationAccount> findAll() {
		return this.em.createQuery(
				"SELECT orgAcc from OrganizationAccount orgAcc left join fetch orgAcc.organizationInfo orgInfo",
				OrganizationAccount.class).getResultList();
	}

	public List<OrganizationAccount> getAllPublishedOrgs() {
		String query = "SELECT orgAcc from OrganizationAccount orgAcc left join fetch orgAcc.organizationInfo orgInfo "
				+ "WHERE orgAcc.accountStatus = fr.isika.cda14.efund.entity.enums.AccountStatus.ACTIVE";
		return this.em.createQuery(query, OrganizationAccount.class).getResultList();
	}

	public OrganizationSpace findOrgSpace(Long id) {
		return em.find(OrganizationSpace.class, id);
	}

	public void update(OrganizationSpace orgSpace) {
		em.merge(orgSpace);
	}

	public void updateOrg(OrganizationAccount org) {
		em.merge(org);
	}

	public OrganizationAccount loadOrganizationAccountWithChildren(Long id) {
		/* On force le Fetching de la collection d'Items dans le Shop */
		String query = "SELECT distinct shop FROM OrganizationAccount org INNER JOIN org.organizationSpace space "
				+ "INNER JOIN space.shop shop LEFT JOIN FETCH shop.items WHERE org.id=:id";
		Shop shop = em.createQuery(query, Shop.class).setParameter("id", id).getSingleResult();

		/* On force le Fetching de la collection de Projects dans le Space */
		query = "SELECT distinct space FROM OrganizationAccount org INNER JOIN org.organizationSpace space "
				+ "LEFT JOIN FETCH space.projects INNER JOIN space.shop shop WHERE space.shop in :shop";
		OrganizationSpace space = em.createQuery(query, OrganizationSpace.class).setParameter("shop", shop)
				.getSingleResult();

		/* On force le Fetching de la collection d'Events dans le Space */
		query = "SELECT distinct space FROM OrganizationAccount org INNER JOIN org.organizationSpace space "
				+ "LEFT JOIN FETCH space.events INNER JOIN space.shop shop WHERE org.organizationSpace in :space";
		space = em.createQuery(query, OrganizationSpace.class).setParameter("space", space).getSingleResult();

		/* On force le Fetching de la collection de ContentTabs dans le Space */
		query = "SELECT distinct space FROM OrganizationAccount org INNER JOIN org.organizationSpace space "
				+ "LEFT JOIN FETCH space.contentBlocks INNER JOIN space.shop shop WHERE org.organizationSpace in :space";
		space = em.createQuery(query, OrganizationSpace.class).setParameter("space", space).getSingleResult();

		/* On relie le tout et on sort notre OrganizationAccount */
		query = "SELECT distinct org FROM OrganizationAccount org INNER JOIN org.organizationSpace space "
				+ "INNER JOIN space.shop shop WHERE org.organizationSpace in :space";
		OrganizationAccount account = em.createQuery(query, OrganizationAccount.class).setParameter("space", space)
				.getSingleResult();

		return account;
	}

	public OrganizationAccount getOrgFromProject(Long id) {
		String query = "SELECT orgAcc FROM OrganizationAccount orgAcc JOIN orgAcc.organizationSpace orgSpace "
				+ "JOIN orgSpace.projects pro WHERE pro.id =:id ";

		return em.createQuery(query, OrganizationAccount.class).setParameter("id", id).getSingleResult();
	}

	public OrganizationAccount getOrgFromEvent(Long id) {
		String query = "SELECT orgAcc FROM OrganizationAccount orgAcc JOIN orgAcc.organizationSpace orgSpace "
				+ "JOIN orgSpace.events ev WHERE ev.id =:id ";

		return em.createQuery(query, OrganizationAccount.class).setParameter("id", id).getSingleResult();
	}

	public List<UserAccount> getAllUsers() {
		String query = "SELECT acc FROM UserAccount acc";
		return em.createQuery(query, UserAccount.class).getResultList();
	}

	public List<OrganizationAccount> getAllOrgs() {
		String query = "SELECT acc FROM OrganizationAccount acc";
		return em.createQuery(query, OrganizationAccount.class).getResultList();
	}

	public void removeUser(UserAccount user) {
		em.remove(user);
	}

	public List<OrganizationAccount> getTopOrgs() {
		String query = "SELECT org FROM OrganizationAccount org ORDER BY org.creationDate";
		return em.createQuery(query, OrganizationAccount.class).setMaxResults(3).getResultList();
	}

	public List<Favorite> getFavorites(Long userSpaceId) {
		String query = "SELECT fav FROM Favorite fav JOIN fav.userSpace usr  WHERE usr.id=:id";
		return em.createQuery(query, Favorite.class).setParameter("id", userSpaceId).getResultList();
	}

	public List<Donation> getDonations(Long userSpaceId) {
		String query = "SELECT don FROM Donation don JOIN don.userSpace usr  WHERE usr.id=:id";
		return em.createQuery(query, Donation.class).setParameter("id", userSpaceId).getResultList();
	}

	public List<EventRegistration> getRegistrations(Long userSpaceId) {
		String query = "SELECT reg FROM EventRegistration reg JOIN reg.userSpace usr WHERE usr.id=:id";
		return em.createQuery(query, EventRegistration.class).setParameter("id", userSpaceId).getResultList();
	}

	public List<BasketOrder> getBasketOrders(Long userSpaceId) {
		String query = "SELECT ord FROM BasketOrder ord JOIN ord.userSpace usr  WHERE usr.id=:id";
		return em.createQuery(query, BasketOrder.class).setParameter("id", userSpaceId).getResultList();
	}

	public ContentBlock findBlock(Long blockId) {
		return em.find(ContentBlock.class, blockId);
	}

	public void removeBlock(ContentBlock block) {
		em.remove(block);
	}
}

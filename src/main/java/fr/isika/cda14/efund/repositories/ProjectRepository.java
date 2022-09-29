package fr.isika.cda14.efund.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.isika.cda14.efund.entity.common.ContentBlock;
import fr.isika.cda14.efund.entity.project.FaqElement;
import fr.isika.cda14.efund.entity.project.Project;
import fr.isika.cda14.efund.entity.project.StretchGoal;

@Stateless
public class ProjectRepository {

	@PersistenceContext
	private EntityManager em;

	public void create(Project project) {
		em.persist(project);
	}

	// recherche d'un projet à partir d'un id
	public Project findProject(Long id) {
		return this.em.find(Project.class, id);
	}

	// HEAVY LOADER - Fetch les collections d'objets
	public Project loadProjectWithChildren(Long projId) {

		/* On force le Fetching de la collection de ContentBlocks dans le Project */
		String query = "SELECT distinct proj FROM Project proj LEFT JOIN FETCH proj.contentBlocks WHERE proj.id=:id";
		Project project = em.createQuery(query, Project.class).setParameter("id", projId).getSingleResult();

		/* On force le Fetching de la collection de FAQs dans le Project */
		query = "SELECT distinct proj FROM Project proj LEFT JOIN FETCH proj.faq faq WHERE proj in :proj";

		project = em.createQuery(query, Project.class).setParameter("proj", project).getSingleResult();

		/* On force le Fetching de la collection de StretchGoals dans le Project */
		query = "SELECT proj FROM Project proj LEFT JOIN FETCH proj.stretchGoals goal WHERE proj in :proj ORDER BY goal.target";

		project = em.createQuery(query, Project.class).setParameter("proj", project).getSingleResult();

		return project;
	}

	public List<Project> findAllPublished() {
		return this.em.createQuery(
				"SELECT pro FROM Project pro WHERE pro.projectStatus = fr.isika.cda14.efund.entity.enums.ProjectStatus.PUBLISHED", Project.class).getResultList();
	}
	
	public List<Project> findAll() {
		return this.em.createQuery("SELECT pro FROM Project pro", Project.class).getResultList();
	}

	// recherche d'un projet par son nom à partir de la page ProjectsList
	public List<Project> searchProjectFromPage(String searchProject) {
		String query = "SELECT projName FROM Project projName WHERE pro.projectStatus = fr.isika.cda14.efund.entity.enums.ProjectStatus.PUBLISHED AND projName.name LIKE CONCAT('%', :searchProject, '%') ";
		return em.createQuery(query, Project.class).setParameter("searchProject", searchProject).getResultList();
	}

	public void remove(Project project) {
		em.remove(project);
	}

	public void update(Project proj) {
		em.merge(proj);
	}

	public List<Project> getTopProjects() {
		String query = "SELECT proj FROM Project proj ORDER BY proj.creationDate";
		return em.createQuery(query, Project.class).setMaxResults(3).getResultList();
	}

	public List<Project> getOrgsProjects(Long orgSpaceId) {
		String query = "SELECT projs FROM OrganizationSpace os JOIN os.projects projs WHERE os.id=:id";
		return em.createQuery(query, Project.class).setParameter("id", orgSpaceId).getResultList();
	}

	/* ContentBlock Methods */
	public ContentBlock findBlock(Long blockId) {
		return em.find(ContentBlock.class, blockId);
	}

	public void removeBlock(ContentBlock block) {
		em.remove(block);
	}

	/* StretchGoal Methods */
	public StretchGoal findGoal(Long goalId) {
		return em.find(StretchGoal.class, goalId);
	}

	public void removeGoal(StretchGoal goal) {
		em.remove(goal);
	}

	/* FAQ methods */
	public FaqElement findFaq(Long faqId) {
		return em.find(FaqElement.class, faqId);
	}

	public void removeFaq(FaqElement faqElement) {
		em.remove(faqElement);
	}
}

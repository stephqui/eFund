package fr.isika.cda14.efund.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.isika.cda14.efund.entity.account.OrganizationAccount;
import fr.isika.cda14.efund.entity.account.UserAccount;
import fr.isika.cda14.efund.entity.common.ContentBlock;
import fr.isika.cda14.efund.entity.enums.ProjectStatus;
import fr.isika.cda14.efund.entity.project.Donation;
import fr.isika.cda14.efund.entity.project.FaqElement;
import fr.isika.cda14.efund.entity.project.Project;
import fr.isika.cda14.efund.entity.project.StretchGoal;
import fr.isika.cda14.efund.entity.space.OrganizationSpace;
import fr.isika.cda14.efund.repositories.AccountRepository;
import fr.isika.cda14.efund.repositories.DonationRepository;
import fr.isika.cda14.efund.repositories.ProjectRepository;
import fr.isika.cda14.efund.tool.SessionTool;
import fr.isika.cda14.efund.viewmodel.ContentVM;
import fr.isika.cda14.efund.viewmodel.DonationVM;
import fr.isika.cda14.efund.viewmodel.FaqVM;
import fr.isika.cda14.efund.viewmodel.GoalVM;
import fr.isika.cda14.efund.viewmodel.ProjectCreationFormVM;

@Stateless
public class ProjectService {

	@Inject
	private ProjectRepository projectRepo;

	@Inject
	private AccountRepository accountRepo;

	@Inject
	private DonationRepository donationRepo;

	@Inject
	StatisticsService statsService;

	public void create(ProjectCreationFormVM projectCreationFormVM, Long orgSpaceId) {
		Project newProject = new Project();
		newProject.setName(projectCreationFormVM.getName());
		newProject.setEndDate(projectCreationFormVM.getEndDate());
		newProject.setSummary(projectCreationFormVM.getSummary());
		newProject.setImagePath(projectCreationFormVM.getImagePath());
		newProject.setLocation(projectCreationFormVM.getLocation());
		newProject.setProjectCategory(projectCreationFormVM.getProjectCategory());
		newProject.setProjectRange(projectCreationFormVM.getProjectRange());
		newProject.setCurrentAmount(new BigDecimal(0));
		newProject.setTargetAmount(projectCreationFormVM.getTargetAmount());
		newProject.setProjectStatus(ProjectStatus.DRAFT);
		newProject.setCreationDate(new Date());

		OrganizationSpace orgSpace = accountRepo.findOrgSpace(orgSpaceId);

		newProject.setOrganizationSpace(orgSpace);

		projectRepo.create(newProject);
		statsService.addProjectToStats();
	}

	public List<Project> findAll() {
		return projectRepo.findAll();
	}

	/* Find on primary key (FAST) */
	public Project findProject(Long id) {
		return projectRepo.findProject(id);
	}

	/* HEAVY LOADER => Fetch les collections en LazyLoading */
	public Project loadProjectWithChildren(Long projId) {
		return projectRepo.loadProjectWithChildren(projId);
	}

	public List<Project> searchProjectFromPage(String searchProject) {
		return projectRepo.searchProjectFromPage(searchProject);
	}

	public void createDonation(DonationVM donationVM, Long id) {
		Donation newDon = new Donation();
		newDon.setAmount(donationVM.getAmount());
		newDon.setFullName(donationVM.getFullName());
		newDon.setCreditCardNumber(donationVM.getCreditCardNumber());
		newDon.setExpirationDate(donationVM.getExpirationDate());
		newDon.setCryptogram(donationVM.getCryptogram());

		UserAccount userAccount = accountRepo.findUser(SessionTool.getUserId());

		newDon.setUserSpace(userAccount.getUserSpace());

		Project project = projectRepo.findProject(id);
		newDon.setProject(project);
		project.setCurrentAmount(project.getCurrentAmount().add(newDon.getAmount()));

		donationRepo.createDonationRepo(newDon);

		statsService.addDonationToStats(newDon.getAmount().intValue());
	}

	public OrganizationAccount getOrgFromProject(Long id) {
		return accountRepo.getOrgFromProject(id);
	}

	public void deleteProject(Long id) {
		Project project = findProject(id);
		projectRepo.remove(project);

		statsService.removeProjectFromStats();
	}

	public void update(Project proj) {
		projectRepo.update(proj);
	}

	public List<Project> getTopProjects() {
		return projectRepo.getTopProjects();
	}

	public List<Project> getOrgsProjects(Long orgSpaceId) {
		return projectRepo.getOrgsProjects(orgSpaceId);
	}

	/* Content Blocks Methods */
	public void addContent(ContentVM content, Project project) {
		ContentBlock newBlock = new ContentBlock();

		newBlock.setContent(content.getContent());
		newBlock.setType(content.getType());
		project.getContentBlocks().add(newBlock);
		projectRepo.update(project);
	}

	public void removeBlock(Long blockId) {
		ContentBlock block = projectRepo.findBlock(blockId);
		projectRepo.removeBlock(block);
	}

	/* StretchGoals Methods */
	public void addGoal(GoalVM goalVM, Project project) {
		StretchGoal newGoal = new StretchGoal();

		newGoal.setTarget(goalVM.getTarget());
		newGoal.setDescription(goalVM.getDescription());
		project.getStretchGoals().add(newGoal);
		projectRepo.update(project);
	}

	public void removeGoal(Long goalId) {
		StretchGoal goal = projectRepo.findGoal(goalId);
		projectRepo.removeGoal(goal);
	}

	public void addFaq(FaqVM faqVM, Project project) {
		FaqElement newFaqElement = new FaqElement();

		newFaqElement.setQuestion(faqVM.getQuestion());
		newFaqElement.setAnswer(faqVM.getAnswer());
		project.getFaq().add(newFaqElement);
		projectRepo.update(project);
	}

	public void removeFaq(Long faqId) {
		FaqElement faqElement = projectRepo.findFaq(faqId);
		projectRepo.removeFaq(faqElement);
	}

	public void changeStatusToSubmit(Long id) {
		Project project = projectRepo.findProject(id);
		project.setProjectStatus(ProjectStatus.SUBMITTED);
		projectRepo.update(project);
	}

	public List<Project> findAllPublished() {
		return projectRepo.findAllPublished();
	}
}

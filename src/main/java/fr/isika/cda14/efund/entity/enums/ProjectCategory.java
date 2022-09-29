package fr.isika.cda14.efund.entity.enums;

public enum ProjectCategory {

	ECOLOGY("Ecologie"), HEALTH("Santé"), EDUCATION("Education"), ART("Art"), RIGHTS("Droits"), OTHER("Autre");

	private String projectCategoryLabel;

	private ProjectCategory(String projectCategoryLabel) {
		this.projectCategoryLabel = projectCategoryLabel;
	}

	public String getProjectCategoryLabel() {
		return projectCategoryLabel;

	}
}

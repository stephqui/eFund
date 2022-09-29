package fr.isika.cda14.efund.entity.enums;

public enum ProjectRange {
	LOCAL("Locale"), REGIONAL("Régionale"), NATIONAL("Nationale"), WORLDWIDE("Mondiale");

	private String projectRangeLabel;

	private ProjectRange(String projectRangeLabel) {
		this.projectRangeLabel = projectRangeLabel;
	}

	public String getProjectRangeLabel() {
		return projectRangeLabel;
	}
}

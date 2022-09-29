package fr.isika.cda14.efund.viewmodel;

import javax.validation.constraints.Size;

public class ContentVM {
	@Size(max = 5000)
	private String content;

	private String type;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

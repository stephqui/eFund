package fr.isika.cda14.efund.tool;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.isika.cda14.efund.entity.account.Account;
import fr.isika.cda14.efund.entity.enums.Role;

public class SessionTool {
	public static void writeInSession(Account account) {

		String dashBoardURL = generateDashBoardURL(account.getRole(), account.getId());

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		session.setAttribute(SessionAttributesUtils.CONNECTED_USER_ID, account.getId());
		session.setAttribute(SessionAttributesUtils.CONNECTED_USER_NAME, account.getDisplayedName());
		session.setAttribute(SessionAttributesUtils.CONNECTED_USER_ROLE, account.getRole().toString());
		session.setAttribute(SessionAttributesUtils.CONNECTED_USER_IMAGE, account.getImagePath());
		session.setAttribute(SessionAttributesUtils.CONNECTED_USER_DASHBOARD, dashBoardURL);
	}

	public static void updateSessionImage(String imagePath) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute(SessionAttributesUtils.CONNECTED_USER_IMAGE, imagePath);
	}

	private static String generateDashBoardURL(Role role, Long id) {
		if (role == Role.ADMIN) {
			return "adminDashboard?id=" + id;
		} else if (role == Role.ASSOC) {
			return "pageOng?id=" + id;
		} else {
			return "userProfil?id=" + id;
		}
	}

	public static void resetSessionAttributes() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
	}

	public static Long getUserId() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		return (Long) session.getAttribute(SessionAttributesUtils.CONNECTED_USER_ID);
	}

	public static String getRole() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		return (String) session.getAttribute(SessionAttributesUtils.CONNECTED_USER_ROLE);
	}

	public static String getUserName() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		return (String) session.getAttribute(SessionAttributesUtils.CONNECTED_USER_NAME);

	}

	public static String getImagePath() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		return (String) session.getAttribute(SessionAttributesUtils.CONNECTED_USER_IMAGE);

	}

	public static String getDashBoardURL() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		return (String) session.getAttribute(SessionAttributesUtils.CONNECTED_USER_DASHBOARD);
	}
}
